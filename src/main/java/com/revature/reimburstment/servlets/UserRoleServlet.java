package com.revature.reimburstment.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.reimburstment.dtos.requests.UserRoleRequest;
import com.revature.reimburstment.dtos.responses.Principal;
import com.revature.reimburstment.models.User;
import com.revature.reimburstment.models.UserRole;
import com.revature.reimburstment.services.TokenService;
import com.revature.reimburstment.services.UserRoleService;
import com.revature.reimburstment.utils.custom_exceptions.InvalidRequestException;
import com.revature.reimburstment.utils.custom_exceptions.ResourceConflictException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserRoleServlet extends HttpServlet {
    private final ObjectMapper mapper;

    private TokenService tokenService;
    private final UserRoleService userRoleService;

    public UserRoleServlet(ObjectMapper mapper, TokenService tokenService, UserRoleService userRoleService) {
        this.mapper = mapper;
        this.userRoleService = userRoleService;
        this.tokenService = tokenService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            UserRoleRequest request = mapper.readValue(req.getInputStream(), UserRoleRequest.class);
            UserRole userRole = userRoleService.saveUserRole(request);
            resp.setContentType("application/json");
            resp.setStatus(200);
            resp.getWriter().write(mapper.writeValueAsString(userRole.getRole_id()));

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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String role = getRole(req, resp);
            if (role.equals("ADMIN")) {
                List<UserRole> userRoles = userRoleService.getAll();
                resp.setContentType("application/json");
                resp.getWriter().write(mapper.writeValueAsString(userRoles));
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
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            UserRoleRequest request = mapper.readValue(req.getInputStream(), UserRoleRequest.class);
            userRoleService.update(request);
            resp.setContentType("application/json");
            resp.setStatus(200);
            resp.getWriter().write(mapper.writeValueAsString("Updated " + request.getRole_id()));

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
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            UserRoleRequest request = mapper.readValue(req.getInputStream(), UserRoleRequest.class);
            userRoleService.delete(request.getRole());
            resp.setContentType("application/json");
            resp.setStatus(200);
            resp.getWriter().write(mapper.writeValueAsString("Deleted " + request.getRole()));

        } catch (InvalidRequestException e) {
            resp.setStatus(404);
            resp.getWriter().write(mapper.writeValueAsString(e.getMessage()));
        } catch (ResourceConflictException e) {
            resp.setStatus(409);
        } catch (Exception e) {
            resp.setStatus(404);
        }
    }
}
