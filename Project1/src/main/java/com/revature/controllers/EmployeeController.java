package com.revature.controllers;

import com.revature.Services.EmployeeServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.exception.InvalidException;
import com.revature.models.Claim;
import com.revature.models.Employee;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

public class EmployeeController extends FrontController{

    private ObjectMapper om = new ObjectMapper();
    private EmployeeServices employeeServices = new EmployeeServices();

    public void process(HttpServletRequest request, HttpServletResponse response) throws IOException{

        String path = (String) request.getAttribute("path");
        System.out.println("Path attribute: " + path);

        if (path == null || path.equals("")) {


            switch (request.getMethod()) {

                case "GET": {
                    // maybe this is a l=place where you want to check a user's permission/role
                    System.out.println("Getting all authors...");

                    List<Employee> authors = employeeServices.getAllfromEmployee();
                    response.getWriter().write(om.writeValueAsString(authors));
                    break;
                }

                case "POST": {
                    System.out.println("Creating new author...");
                    Employee a = om.readValue(request.getReader(), Employee.class);
                    // or Author a = om.readValue(request.getInputStream(), Author.class);

//                    a.setId((authorService.createAuthor(a)).getId());
                    a = employeeServices.createemployee(a);
                    //response.setStatus(201); // Resource created
                    response.getWriter().write(om.writeValueAsString(a));
                    break;
                }

            }


        } else {

            int employee_id = Integer.parseInt(path);

            switch (request.getMethod()) {

                case "GET": {
                    Employee a = employeeServices.searchbyemployeeId(employee_id);
                    if (a != null) {
                        response.getWriter().write(om.writeValueAsString(a));
                    } else {
                        response.sendError(404, "Employee not found");
                    }
                    break;
                }

                case "PUT": {
                    Employee a = om.readValue(request.getReader(), Employee.class);
                    employeeServices.updateemployee(a);
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                    // 204 - server doesn't have any content to send back, but the request was successful
                    break;
                }

                case "DELETE": {
                   employeeServices.deleteAuthor(employee_id);
                    response.setStatus(204);
                    break;
                }

                default: {
                    response.sendError(405);
                    break;
                }
            }

        }

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        process(request,response);
    }

    protected void isAuthorizedToApprove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getHeader("current_user");
        response.setContentType("text/html");
        Employee employee = employeeServices.getEmployeeByUsername(username);

        boolean approved = false;
        if(employee == null) {
            response.getWriter().println(approved);
            return;
        }

        approved = employee.getBenco();
        if(approved) {
            response.getWriter().println(approved);
            return;
        }

        approved = approved || employeeServices.isDirectSupervisor(employee.getEmployee_id());
        if(approved){
            response.getWriter().println(approved);
            return;
        }

        approved = approved || employeeServices.isDepartmentHead(employee.getEmployee_id());
        response.getWriter().println(approved);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            isAuthorizedToApprove(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }


}
