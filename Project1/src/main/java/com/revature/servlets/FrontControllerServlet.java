//package com.revature.servlets;
//
//import com.revature.controllers.FrontController;
//import com.revature.exception.InvalidException;
//import org.apache.catalina.servlets.DefaultServlet;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//public class FrontControllerServlet extends HttpServlet {
//
//    private Logger log = LogManager.getLogger(FrontControllerServlet.class);
//    private RequestHandler rh = new RequestHandler();
//
//    // This method is where we will funnel all of our requests
//    // This way - we have one central location, and can pass them off to some handler/controller
//
//
//
//
//    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//        // first check if trying to access static resources
//        String uriNoContext = request.getRequestURI().substring(request.getContextPath().length());
//        log.trace(uriNoContext);
//
//        if (uriNoContext.startsWith("/static")) {
//            log.trace("Accessing static resources and trying to forward....");
//            super.doGet(request, response);
//        } else {
//            // We want to 'get' the correct servlet based on the uri
//            log.trace("Not static...Getting the appropriate controller...");
//            FrontController fc = rh.handle(request, response);
//
//            // check first to make sure it's not null
//            if (fc != null) {
//                log.trace("Processing request...");
//                try {
//                    fc.process(request, response);
//                } catch (InvalidException e) {
//                    e.printStackTrace();
//                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//                }
//            } else {
//                response.sendError(HttpServletResponse.SC_NOT_FOUND);
//            }
//        }
//
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        process(req, resp);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        process(req, resp);
//    }
//
//    @Override
//    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        process(req, resp);
//    }
//
//    @Override
//    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        process(req, resp);
//    }
//}
