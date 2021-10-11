package com.revature.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.Services.*;
import com.revature.exception.InvalidException;
import com.revature.models.ApprovalRequest;
import com.revature.models.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

//@WebServlet("/approve")
public class ApprovalController extends FrontController  {

    private ObjectMapper om = new ObjectMapper();
    private Logger log = LogManager.getLogger(ApprovalController.class);
    private BenefitsCoordinatorService benefitsCoordinatorService = new BenefitsCoordinatorService();
    private DirectSupervisorService directSupervisorService = new DirectSupervisorService();
    private DepartmentHeadService departmentHeadService = new DepartmentHeadService();
    private EmployeeServices employeeServices = new EmployeeServices();
    private ValidationService validationService = new ValidationService();
    private ClaimService claimService = new ClaimService();

    public void process(HttpServletRequest request, HttpServletResponse response) throws IOException, InvalidException {
        String path = (String) request.getAttribute("path");
        System.out.println("Path attribute: " + path);

        if(!request.getMethod().contentEquals("POST")) {
            throw new InvalidException("Should be a POST request");
        }

        ApprovalRequest approvalRequest = new ApprovalRequest();
        approvalRequest.setApproval_type(request.getParameter("approval_type"));
        approvalRequest.setClaim_id(Integer.parseInt(request.getParameter("claim_id")));
        approvalRequest.setComment(request.getParameter("comment"));
        approvalRequest.setApprover_username(request.getParameter("approver_username"));
        if(request.getParameter("amount") != null && !request.getParameter("amount").isEmpty()) {
            approvalRequest.setAmount(Double.parseDouble(request.getParameter("amount")));
        }

        validationService.validateApprovalRequest(approvalRequest);

        String username = "";
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if(cookie.getName().contentEquals("username"))
                username = cookie.getValue();
        }
        Employee employee = employeeServices.getEmployeeByUsername(username);

        // For direct supervisor actions
        if(employeeServices.isDirectSupervisor(employee.getEmployee_id())) {
            if(approvalRequest.getApproval_type().contentEquals("APPROVED"))
                directSupervisorService.approveClaim(approvalRequest.getClaim_id(), employee.getEmployee_id());
            else if(approvalRequest.getApproval_type().contentEquals("REJECTED"))
                directSupervisorService.reject(approvalRequest.getClaim_id(), approvalRequest.getComment());
            else if(approvalRequest.getApproval_type().startsWith("NEED_INFO_FROM"))
                directSupervisorService.askForMoreInfo(approvalRequest.getClaim_id(), approvalRequest.getComment(),approvalRequest.getApproval_type());
            else if(approvalRequest.getApproval_type().contentEquals("APPROVE_PRESENTATION"))
                directSupervisorService.approvePresentation(approvalRequest.getClaim_id(), true);
            else if(approvalRequest.getApproval_type().contentEquals("REJECT_PRESENTATION"))
                directSupervisorService.approvePresentation(approvalRequest.getClaim_id(), false);
            else
                throw new InvalidException("Invalid approval type");
        }

        // For department head actions
        else if(employeeServices.isDepartmentHead(employee.getEmployee_id())) {
            if(approvalRequest.getApproval_type().contentEquals("APPROVED"))
                departmentHeadService.approve(approvalRequest.getClaim_id());
            else if(approvalRequest.getApproval_type().contentEquals("REJECTED"))
                departmentHeadService.reject(approvalRequest.getClaim_id(), approvalRequest.getComment());
            else if(approvalRequest.getApproval_type().startsWith("NEED_INFO_FROM"))
                departmentHeadService.askForMoreInfo(approvalRequest.getClaim_id(), approvalRequest.getComment(),approvalRequest.getApproval_type());
            else
                throw new InvalidException("Invalid approval type");
        }

        // For benco actions
        else if(employee.getBenco()) {
            if(approvalRequest.getApproval_type().contentEquals("APPROVED"))
                benefitsCoordinatorService.approve(approvalRequest.getClaim_id());
            else if(approvalRequest.getApproval_type().contentEquals("REJECTED"))
                benefitsCoordinatorService.reject(approvalRequest.getClaim_id(), approvalRequest.getComment());
            else if(approvalRequest.getApproval_type().startsWith("NEED_INFO_FROM"))
                benefitsCoordinatorService.askForMoreInfo(approvalRequest.getClaim_id(), approvalRequest.getComment(),approvalRequest.getApproval_type());
            else if(approvalRequest.getApproval_type().contentEquals("APPROVE_GRADE"))
                benefitsCoordinatorService.approveGrade(approvalRequest.getClaim_id(), true);
            else if(approvalRequest.getApproval_type().contentEquals("REJECT_GRADE"))
                benefitsCoordinatorService.approveGrade(approvalRequest.getClaim_id(), false);
            else if(approvalRequest.getApproval_type().contentEquals("CHANGE_AMOUNT"))
                benefitsCoordinatorService.changeClaimAmount(approvalRequest.getClaim_id(), approvalRequest.getAmount(), approvalRequest.getComment());
            else
                throw new InvalidException("Invalid approval type");
        } else {
            if(approvalRequest.getApproval_type().contentEquals("CANCEL"))
                claimService.cancelClaim(approvalRequest.getClaim_id());
            else if(approvalRequest.getApproval_type().contentEquals("ADD_GRADE"))
                claimService.addSecuredGrade(approvalRequest.getClaim_id(), approvalRequest.getComment());
            else if(approvalRequest.getApproval_type().contentEquals("ADD_EVENT_ATTACHMENTS"))
                claimService.addEventAttachments(approvalRequest.getClaim_id(), approvalRequest.getComment());
            else
                throw new InvalidException("Invalid approval type");
        }
        System.out.println(request.getRequestURI());
        response.sendRedirect(request.getHeader("referer"));

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            process(request,response);
        } catch (InvalidException e) {
            e.printStackTrace();
        }
    }
}
