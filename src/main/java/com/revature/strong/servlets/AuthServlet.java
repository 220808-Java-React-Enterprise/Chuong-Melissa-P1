package com.revature.strong.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.strong.dtos.request.LoginRequest;
import com.revature.strong.dtos.response.Principal;
import com.revature.strong.services.TokenService;
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
    private final TokenService tokenService;
    private final UserService userService;

    public AuthServlet(ObjectMapper mapper, TokenService tokenService, UserService userService){
        this.mapper = mapper;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            LoginRequest request = mapper.readValue(req.getInputStream(), LoginRequest.class);
            Principal principal = userService.login(request);
            String token = tokenService.generateToken(principal);

            resp.setStatus(200);
            resp.setHeader("Authorization", token);
            resp.setContentType("application/json");
            resp.getWriter().write(mapper.writeValueAsString(principal));

        }catch (InvalidRequestException e){
            resp.setStatus(404);
        }catch (AuthenticationException e){
            resp.setStatus(401);
        }
    }
}
