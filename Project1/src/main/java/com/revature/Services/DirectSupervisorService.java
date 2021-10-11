package com.revature.Services;

import com.revature.Repo.Hibernate.ClaimHibernate;
import com.revature.exception.InvalidException;
import com.revature.models.Claim;

public class DirectSupervisorService {

    private ClaimHibernate claimHibernate = new ClaimHibernate();
    private ValidationService validationService = new ValidationService();
    private EmployeeServices employeeServices = new EmployeeServices();
    private ClaimEventService claimEventService = new ClaimEventService();

    public void reject(Integer claim_id, String message) throws InvalidException {
        validationService.validateMessage(message);
        Claim claim = claimHibernate.getById(claim_id);
        claim.setApproval_direct_supervisor("REJECTED");
        claim.setComment_direct_supervisor(message);
        claimHibernate.updateClaim(claim);
    }

    public void approveClaim(Integer claim_id, Integer current_employee_id) {
        Claim claim = claimHibernate.getById(claim_id);
        claim.setApproval_direct_supervisor("APPROVED");
        if(employeeServices.isDepartmentHead(current_employee_id)) {
            claim.setApproval_department_head("APPROVED");
        }
        claimHibernate.updateClaim(claim);
    }

    public void askForMoreInfo(Integer claim_id, String message, String type) throws InvalidException {
        validationService.validateMoreInfoRequestByDirectSupervisor(type);
        Claim claim = claimHibernate.getById(claim_id);
        claim.setApproval_direct_supervisor(type);
        claim.setComment_direct_supervisor(message);
        claimHibernate.updateClaim(claim);
    }

    public void approvePresentation(Integer claim_id, boolean approved) {
        Claim claim = claimHibernate.getById(claim_id);
        claimEventService.approveEventPresentation(claim.getClaimEvent().getEvent_id(), approved);
    }


}
