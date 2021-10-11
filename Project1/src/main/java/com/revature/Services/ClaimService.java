package com.revature.Services;

import com.revature.Repo.Hibernate.ClaimHibernate;
import com.revature.exception.InvalidException;
import com.revature.models.Claim;
import com.revature.models.ClaimEvent;
import com.revature.models.Employee;

import java.time.LocalDate;
import java.util.List;

public class ClaimService {

    private ValidationService validationService = new ValidationService();
    private ClaimEventService claimEventService = new ClaimEventService();
    private EmployeeServices employeeServices = new EmployeeServices();
    private DepartmentService departmentService = new DepartmentService();
    private ClaimHibernate claimHibernate = new ClaimHibernate();


    public Claim getClaimById(Integer id) {
        return claimHibernate.getById(id);
    }

    public Claim updateClaim(Claim claim) {
        return claimHibernate.updateClaim(claim);
    }

    public Claim createClaim(Claim claim, ClaimEvent claimEvent, String currentUserName) throws InvalidException {
        validationService.validateClaimEventInput(claimEvent);
        validationService.validateClaimInput(claim);

        Employee currentEmployee = employeeServices.getEmployeeByUsername(currentUserName);
        claim.setEmployee_id(currentEmployee.getEmployee_id());

        switch(claimEvent.getEvent_type()) {
            case "University_Courses":
                claim.setClaim_amount(claimEvent.getEvent_cost()*0.8);
                break;
            case "Seminars":
                claim.setClaim_amount(claimEvent.getEvent_cost()*0.6);
                break;
            case "Certification_Preparation_Classes":
                claim.setClaim_amount(claimEvent.getEvent_cost()*0.75);
                break;
            case "Certification":
                claim.setClaim_amount(claimEvent.getEvent_cost()*1);
                break;
            case "Technical_Training":
                claim.setClaim_amount(claimEvent.getEvent_cost()*0.9);
                break;
            case "Other":
                claim.setClaim_amount(claimEvent.getEvent_cost()*0.3);
                break;
        }


        Double total_amount = getClaimAmountForLastYear(currentEmployee.getEmployee_id());
        if(total_amount > 1000) {
            claim.setClaim_amount(0.0);
        } else if(total_amount + claim.getClaim_amount() > 1000) {
            claim.setClaim_amount(1000-total_amount);
        }

        claim.setApproval_direct_supervisor("NEED_APPROVAL");
        claim.setApproval_department_head("NEED_APPROVAL");
        claim.setApproval_benco("NEED_APPROVAL");

        if(claim.getApproval_attachement_direct_supervisor() != null &&
                !claim.getApproval_attachement_direct_supervisor().isEmpty()) {
            claim.setApproval_direct_supervisor("APPROVED");
            if(employeeServices.isDepartmentHead(currentEmployee.getDirect_supervisor_id())) {
                claim.setApproval_department_head("APPROVED");
            }
        }

        if(claim.getApproval_attachement_department_head() != null &&
                !claim.getApproval_attachement_department_head().isEmpty()) {
            claim.setApproval_department_head("APPROVED");
        }
        if(claimEvent.getGrading_format().contentEquals("Grade") && (claimEvent.getPassing_grade() == null || claimEvent.getPassing_grade().isEmpty())) {
            claimEvent.setPassing_grade("60%");
        }

        if(LocalDate.now().plusDays(14).isAfter(LocalDate.parse(claimEvent.getEvent_date()))) {
            claim.setUrgent(true);
            claim.setClaim_status("PENDING(URGENT)");
        } else {
            claim.setUrgent(false);
        }

        if(LocalDate.now().isAfter(LocalDate.now().minusDays(2))) {
            if(claim.getApproval_benco() != "APPROVED") {
                claim.setApproval_department_head("AUTO_APPROVED");
            }

            if(claim.getApproval_direct_supervisor() != "APPROVED") {
                claim.setApproval_benco("EMAIL SENT TO DIRECT SUPERVISOR");
            }


        }




        claim.setNew_amount(claim.getClaim_amount());

        System.out.println(claim);
        claimEvent = claimEventService.create(claimEvent);
        claim.setClaimEvent(claimEvent);
        claimHibernate.createClaim(claim);

        return claim;
    }

    public List<Claim> getAllClaimsForUser(String currentUserName) {
        Employee currentEmployee = employeeServices.getEmployeeByUsername(currentUserName);
        return claimHibernate.getClaimsForEmployee(currentEmployee.getEmployee_id());
    }

    public List<Claim> getClaimsUnderDirectSupervisor(String currentUserName) {
        Employee currentEmployee = employeeServices.getEmployeeByUsername(currentUserName);
        return claimHibernate.getClaimsUnderDirectSupervisor(currentEmployee.getEmployee_id());
    }

    public List<Claim> getAllClaims() {
        return claimHibernate.getAllClaims();
    }



    public Double getClaimAmountForLastYear(int employee_id) {
        List<Claim> claims = claimHibernate.getClaimsForEmployeeForLastYear(employee_id);
        return claims.stream().mapToDouble(Claim::getNew_amount).sum();
    }


    public void cancelClaim(Integer claim_id) {
        Claim claim = claimHibernate.getById(claim_id);
        claim.setClaim_status("CANCELLED");
        claimHibernate.updateClaim(claim);
    }

    public void updateClaimAmount(Integer claim_id, Double new_amount, String reason) {
        Claim claim = claimHibernate.getById(claim_id);
        claim.setNew_amount(new_amount);
        claim.setAlter_reason(reason);
        claimHibernate.updateClaim(claim);
    }

    public void addSecuredGrade(Integer claim_id, String secured_grade) throws InvalidException {
        validationService.validSecuredGrade(secured_grade);
        Claim claim = claimHibernate.getById(claim_id);
        claimEventService.addSecuredGrade(claim.getClaimEvent().getEvent_id(), secured_grade);
    }

    public void addEventAttachments(Integer claim_id, String attachment) throws InvalidException {
        validationService.validateIfStringPresent(attachment);
        Claim claim = claimHibernate.getById(claim_id);
        claimEventService.addEventAttachments(claim.getClaimEvent().getEvent_id(), attachment);
    }



}
