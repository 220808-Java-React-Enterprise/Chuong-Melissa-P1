package com.revature.strong.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.strong.services.UserService;
import com.revature.strong.utils.custom_exceptions.AuthenticationException;
import com.revature.strong.utils.custom_exceptions.InvalidRequestException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthServlet extends HttpServlet {
    private final ObjectMapper mapper;
    private final UserService userService;

    public AuthServlet(ObjectMapper mapper, UserService userService){
        this.mapper = mapper;
        this.userService = userService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{


        }catch (InvalidRequestException e){
            resp.setStatus(404);
        }catch (AuthenticationException e){
            resp.setStatus(401);
        }
    }
}
