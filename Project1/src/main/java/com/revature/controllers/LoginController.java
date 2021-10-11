package com.revature.controllers;

import com.revature.Services.EmployeeServices;
import com.revature.models.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends FrontController {

    private Logger log = LogManager.getLogger(LoginController.class);
    private EmployeeServices employeeServices = new EmployeeServices();

    public void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
       String username = request.getParameter("username");
       String password = request.getParameter("password");
        log.info("User logging in ... {}", username);


        System.out.println("Username: " + username + " Password: " + password);
        if (employeeServices.login(username, password)) {
            response.addCookie(new Cookie("username",username));
            String scheme = request.getScheme();
            String serverName = request.getServerName();
            int serverPort = request.getServerPort();
            String contextPath = request.getContextPath();
            String resultPath = scheme + "://" + serverName + ":" + serverPort + contextPath;

            Employee employee = employeeServices.getEmployeeByUsername(username);
            request.setAttribute("current_username",username );
            if(employee.getBenco()) {
                response.addCookie(new Cookie("home", resultPath+"/benco.html"));
                response.sendRedirect("benco.html");
            }
            else if(employeeServices.isDepartmentHead(employee.getEmployee_id())) {
                response.addCookie(new Cookie("home", resultPath+"/department-head.html"));
                response.sendRedirect("department-head.html");
            }
            else if(employeeServices.isDirectSupervisor(employee.getEmployee_id())) {
                response.addCookie(new Cookie("home", resultPath+"/direct_supervisor.html"));
                response.sendRedirect("direct_supervisor.html");
            }
            else {
                response.addCookie(new Cookie("home",resultPath+"/employee-claims.html"));
                response.sendRedirect("employee-claims.html");

            }

        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid login credentials");
        }


    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            process(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }
}
