package com.revature.reimburstment.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.reimburstment.dtos.requests.LoginRequest;
import com.revature.reimburstment.dtos.responses.Principal;
import com.revature.reimburstment.services.TokenService;
import com.revature.reimburstment.services.UserService;
import com.revature.reimburstment.utils.custom_exceptions.AuthenticationException;
import com.revature.reimburstment.utils.custom_exceptions.InvalidRequestException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthServlet extends HttpServlet {
    private final ObjectMapper mapper;
    private final TokenService tokenService;
    private final UserService userService;

    public AuthServlet(ObjectMapper mapper, TokenService tokenService, UserService userService) {
        this.mapper = mapper;
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.invalidate();
        resp.setContentType("application/json");
        resp.getWriter().write(mapper.writeValueAsString("You logged out"));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            LoginRequest request = mapper.readValue(req.getInputStream(), LoginRequest.class);


            Principal principal = userService.login(request);
            String token = tokenService.generateToken(principal);

            resp.setContentType("application/json");
            resp.getWriter().write(mapper.writeValueAsString("Welcome " + principal));
//
//            HttpSession session = req.getSession(true);
//            session.setAttribute("token", token);
//
//            resp.setStatus(200);
//            resp.setHeader("Authoriztion", token);
//            resp.setContentType("application/json");
//            resp.getWriter().write(mapper.writeValueAsString("Welcome " + principal.getUsername()));

        }catch(InvalidRequestException e) {
            resp.setStatus(404);
            resp.getWriter().write(mapper.writeValueAsString(e.getMessage()));
        }catch (AuthenticationException e) {
            resp.setStatus(401);
            resp.getWriter().write(mapper.writeValueAsString(e.getMessage()));
        }
    }
}
