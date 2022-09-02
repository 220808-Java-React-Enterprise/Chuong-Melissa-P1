package com.revature.reimburstment.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.reimburstment.daos.ReimburstmentTypeDAO;
import com.revature.reimburstment.dtos.requests.NewUserRequest;
import com.revature.reimburstment.dtos.responses.Principal;
import com.revature.reimburstment.models.ReimburstmentType;
import com.revature.reimburstment.models.User;
import com.revature.reimburstment.models.UserRole;
import com.revature.reimburstment.services.ReimburstmentTypeService;
import com.revature.reimburstment.services.TokenService;
import com.revature.reimburstment.services.UserRoleService;
import com.revature.reimburstment.services.UserService;
import com.revature.reimburstment.utils.custom_exceptions.InvalidRequestException;
import com.revature.reimburstment.utils.custom_exceptions.ResourceConflictException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserServlet extends HttpServlet  {

    private final ObjectMapper mapper;

    private TokenService tokenService;
    private final UserService userService;

    private UserRoleService userRoleService;

    public UserServlet(ObjectMapper mapper, TokenService tokenService, UserService userService, UserRoleService userRoleService) {
        this.mapper = mapper;
        this.tokenService = tokenService;
        this.userService = userService;
        this.userRoleService = userRoleService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String role = getRole(req, resp);
            if (role.equals("ADMIN")) {
                List<User> userList = userService.getAllUsers();
                resp.setContentType("application/json");
                resp.getWriter().write(mapper.writeValueAsString(userList));
            } else {
                resp.setContentType("application/json");
                resp.getWriter().write(mapper.writeValueAsString(role));
            }
        }catch(NullPointerException e) {
            resp.setStatus(401);
        }
    }

    // only ADMIN and FINANCE users are allowed to administer the system
    private String getRole(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String token = req.getHeader("Authorization");
            Principal principal = tokenService.extractRequesterDetails(token);
            String roleId = principal.getRole_id();
            UserRole userRole = userRoleService.getById(roleId);

            return userRole.getRole();
        }catch (NullPointerException e) {

        }catch (Exception e) {

        }
        return null;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            NewUserRequest request = mapper.readValue(req.getInputStream(), NewUserRequest.class);
            User createdUser = userService.register(request);
            resp.setContentType("application/json");
            resp.setStatus(200);
            resp.getWriter().write(mapper.writeValueAsString(createdUser.getUser_id()));

        } catch (InvalidRequestException e) {
            resp.setStatus(404);
            resp.getWriter().write(mapper.writeValueAsString(e.getMessage()));
        } catch (ResourceConflictException e) {
            resp.setStatus(409);
        } catch (Exception e) {
            resp.setStatus(404);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> userList = userService.getAllUsers();
        resp.setContentType("application/json");
        resp.getWriter().write(mapper.writeValueAsString(userList));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> userList = userService.getAllUsers();
        resp.setContentType("application/json");
        resp.getWriter().write(mapper.writeValueAsString(userList));
    }
}
