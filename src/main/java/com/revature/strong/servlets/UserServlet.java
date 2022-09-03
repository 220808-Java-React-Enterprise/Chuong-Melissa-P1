package com.revature.strong.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.strong.dtos.request.NewUserRequest;
import com.revature.strong.dtos.response.Principal;
import com.revature.strong.models.User;
import com.revature.strong.services.TokenService;
import com.revature.strong.services.UserService;
import com.revature.strong.utils.custom_exceptions.InvalidRequestException;
import com.revature.strong.utils.custom_exceptions.ResourceConflictException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserServlet extends HttpServlet {
    private final ObjectMapper mapper;
    private final UserService userService;
    private final TokenService tokenService;

    public UserServlet(ObjectMapper objectMapper, UserService userService, TokenService tokenService){
        this.mapper = objectMapper;
        this.userService = userService;
        this.tokenService = tokenService;
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
            resp.getWriter().write(mapper.writeValueAsString(e.getMessage()));
        } catch (ResourceConflictException e) {
            resp.setStatus(409); //conflict
        } catch (Exception e) {
            resp.setStatus(404);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getHeader("Authorization");
        Principal principal = tokenService.extractRequesterDetails(token);

        try {
            if(principal.getCoach().equals(true)) {
                String username = req.getParameter("username");

                resp.setContentType("application/json");
                if (username != null){
                    resp.getWriter().write(mapper.writeValueAsString(userService.getUserByUsername(username)));
                } else {
                    List<User> userList = userService.getAllUsers();
                    resp.getWriter().write(mapper.writeValueAsString(userList));
                }
            } else {
                resp.setStatus(403);//forbidden
            }
        } catch (NullPointerException e) {
            resp.setStatus(401);
        }
    }
}
