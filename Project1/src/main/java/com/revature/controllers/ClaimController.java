package com.revature.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.Services.ClaimService;
import com.revature.Services.EmployeeServices;
import com.revature.exception.InvalidException;
import com.revature.models.ApprovalRequest;
import com.revature.models.Claim;
import com.revature.models.ClaimEvent;
import com.revature.models.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class ClaimController  extends FrontController {

    private Logger log = LogManager.getLogger(ClaimController.class);
    private ClaimService claimService = new ClaimService();
    private EmployeeServices employeeServices = new EmployeeServices();

    protected void create(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ClaimEvent claimEvent = new ClaimEvent();
        claimEvent.setDescription(request.getParameter("event_description"));
        claimEvent.setEvent_date(request.getParameter("event_date"));
        claimEvent.setEvent_time(request.getParameter("event_time"));
        claimEvent.setEvent_location(request.getParameter("event_location"));
        claimEvent.setEvent_type(request.getParameter("event_type"));
        claimEvent.setGrading_format(request.getParameter("grading_format"));
        claimEvent.setPassing_grade(request.getParameter("passing_grade"));
        claimEvent.setAttachments(request.getParameter("event_attachments"));

        Claim claim = new Claim();
        claim.setWork_related_justification(request.getParameter("work_related_justification"));
        claim.setClaim_amount(Double.parseDouble(request.getParameter("claim_amount")));
        if(request.getParameter("work_time_missed") != null && !request.getParameter("work_time_missed").isEmpty()) {
            claim.setWork_time_missed(Double.parseDouble(request.getParameter("work_time_missed")));
        }
        claim.setApproval_attachement_direct_supervisor(request.getParameter("approval_attachement_direct_supervisor"));
        claim.setApproval_attachement_department_head(request.getParameter("approval_attachement_department_head"));
        claim.setClaim_status("PENDING");
        claimEvent.setEvent_cost(claim.getClaim_amount());

        String username = request.getParameter("current_user");
        try {
            claimService.createClaim(claim, claimEvent, username);
            response.sendRedirect("claimform.html");
        } catch (InvalidException | IOException e) {
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        create(request,response);
    }

    protected void getClaims(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getHeader("current_user");
        response.setContentType("text/html");
        List<Claim> list = new ArrayList<>();
        Employee employee = employeeServices.getEmployeeByUsername(username);
        if(employeeServices.isDirectSupervisor(employee.getEmployee_id())) {
            list = claimService.getClaimsUnderDirectSupervisor(username);
        } else if(employee.getBenco() || employeeServices.isDepartmentHead(employee.getEmployee_id())) {
            list = claimService.getAllClaims();
        } else {
            list = claimService.getAllClaimsForUser(username);
        }



        final StringWriter out =new StringWriter();
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, list);
        PrintWriter p = response.getWriter();
        p.println(out.toString());
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        getClaims(request,response);
    }

}
