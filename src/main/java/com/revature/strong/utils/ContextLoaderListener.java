package com.revature.strong.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.strong.daos.UserDAO;
import com.revature.strong.services.UserService;
import com.revature.strong.servlets.TestServlet;
import com.revature.strong.servlets.UserServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextLoaderListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //Object mapper provides functionality for reading and writing JSON, either to and from basic POJOS (Plain old Java objects)
        ObjectMapper mapper = new ObjectMapper();

        //dependancy Injection
        TestServlet testServlet = new TestServlet();
        UserServlet userServlet = new UserServlet(mapper, new UserService(new UserDAO()));

        //Need ServletContext class to map whatever servlet to url path.
        ServletContext context = sce.getServletContext();
        context.addServlet("TestServlet", testServlet).addMapping("/test");
        context.addServlet("UserServlet", userServlet).addMapping("/users/*");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Shutting down Strong web application");
    }
}
