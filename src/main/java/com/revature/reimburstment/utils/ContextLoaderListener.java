package com.revature.reimburstment.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.reimburstment.daos.ReimburstmentTypeDAO;
import com.revature.reimburstment.daos.UserDAO;
import com.revature.reimburstment.daos.UserRoleDAO;
import com.revature.reimburstment.services.ReimburstmentTypeService;
import com.revature.reimburstment.services.TokenService;
import com.revature.reimburstment.services.UserRoleService;
import com.revature.reimburstment.services.UserService;
import com.revature.reimburstment.servlets.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextLoaderListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        /* ObjectMapper provides functionality for reading and writing JSON, either to and from basic POJOs (Plain Old Java Objects) */
        ObjectMapper mapper = new ObjectMapper();

        /* Dependency Injection */
        TestServlet testServlet = new TestServlet();
        AuthServlet authServlet = new AuthServlet(mapper, new TokenService(new JwtConfig()), new UserService(new UserDAO()));
        UserRegisterServlet userRegisterServlet = new UserRegisterServlet(mapper, new UserService(new UserDAO()));
        UserServlet userServlet = new UserServlet(mapper, new UserService(new UserDAO()));

        UserRoleServlet userRoleServlet = new UserRoleServlet(mapper, new UserRoleService(new UserRoleDAO()));


        ReimburstmentTypeServlet reimburstmentTypeServlet =
                new ReimburstmentTypeServlet(mapper, new ReimburstmentTypeService(new ReimburstmentTypeDAO())  );

        /* Need ServletContext class to map whatever servlet to url path. */
        ServletContext context = sce.getServletContext();
        context.addServlet("TestServlet", testServlet).addMapping("/test");
        context.addServlet("UserRegisterServlet", userRegisterServlet).addMapping("/users/register");
        context.addServlet("UserServlet", userServlet).addMapping("/users");
        context.addServlet("UserRoleServlet", userRoleServlet).addMapping("/role");
        context.addServlet("AuthServlet", authServlet).addMapping("/users/auth");


        context.addServlet("ReimburstmentTypeServlet", reimburstmentTypeServlet).addMapping("/type");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("\nShutting down REIMBURSTMENT web application");
    }
}
