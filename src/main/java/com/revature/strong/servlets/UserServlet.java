package com.revature.strong.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.strong.dtos.request.NewUserRequest;
import com.revature.strong.models.User;
import com.revature.strong.services.UserService;
import com.revature.strong.utils.custom_exceptions.InvalidRequestException;
import com.revature.strong.utils.custom_exceptions.ResourceConflictException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserServlet extends HttpServlet {
    private final ObjectMapper mapper;
    private final UserService userService;

    public UserServlet(ObjectMapper mapper, UserService userService) {
        this.mapper = mapper;
        this.userService = userService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            //new user request from Postman
            //mapper obj convert JSON request and store into a NewUserRequest.class
            NewUserRequest request = mapper.readValue(req.getInputStream(), NewUserRequest.class);

            String[] path = req.getRequestURI().split("/");

            if (path[3].equals("signup")){
                User createduser = userService.register(request);

                resp.setStatus(200); //created should be default but just to show
                resp.setContentType("application/json");
                resp.getWriter().write(mapper.writeValueAsString(createduser.getId()));
            } else{

            }
        } catch (InvalidRequestException e) {
            resp.setStatus(404); //bad request
        } catch (ResourceConflictException e) {
            resp.setStatus(409); //conflict
        }
    }
}
