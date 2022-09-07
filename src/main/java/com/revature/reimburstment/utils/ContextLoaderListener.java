package com.revature.reimburstment.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.reimburstment.daos.*;
import com.revature.reimburstment.models.UserRole;
import com.revature.reimburstment.services.*;
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
        UserServlet userServlet = new UserServlet(mapper, new TokenService(new JwtConfig()), new UserService(new UserDAO()), new RoleService(new RoleDAO()));


        UserRoleServlet userRoleServlet = new UserRoleServlet(mapper,  new TokenService(new JwtConfig()), new RoleService(new RoleDAO()));
        ReimburstmentTypeServlet reimburstmentTypeServlet =
                new ReimburstmentTypeServlet(
                        mapper,
                        new TokenService(new JwtConfig()),
                        new ReimburstmentTypeService(new ReimburstmentTypeDAO()),
                        new RoleService(new RoleDAO()));
        ReimburstStatusServlet userReimburstStatusServlet =
                new ReimburstStatusServlet(
                        mapper,
                        new TokenService(new JwtConfig()),
                        new ReimburstStatusService(new ReimburstStatusDAO()),
                        new RoleService(new RoleDAO()));

        ReimburstmentServlet reimburstmentServlet = new ReimburstmentServlet(
                mapper,
                new TokenService(new JwtConfig()),
                new ReimburstService(new ReimburstDAO(), new ReimburstmentTypeDAO()));


        /* Need ServletContext class to map whatever servlet to url path. */
        ServletContext context = sce.getServletContext();
        context.addServlet("TestServlet", testServlet).addMapping("/test");
        context.addServlet("UserServlet", userServlet).addMapping("/users");
        context.addServlet("AuthServlet", authServlet).addMapping("/users/auth");

        context.addServlet("UserRoleServlet", userRoleServlet).addMapping("/role");
        context.addServlet("ReimburstmentTypeServlet", reimburstmentTypeServlet).addMapping("/type");
        context.addServlet("UserReimburstStatusServlet", userReimburstStatusServlet).addMapping("/status");

        context.addServlet("ReimburstmentServlet", reimburstmentServlet).addMapping("/reim");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("\nShutting down REIMBURSTMENT web application");
    }
}
