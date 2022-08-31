package com.revature.yolp.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.yolp.dtos.requests.NewUserRequest;
import com.revature.yolp.dtos.requests.ReimburstmentTypeRequest;
import com.revature.yolp.dtos.requests.UserRoleRequest;
import com.revature.yolp.models.User;
import com.revature.yolp.models.UserRole;
import com.revature.yolp.services.UserRoleService;
import com.revature.yolp.services.UserService;
import com.revature.yolp.utils.custom_exceptions.InvalidRequestException;
import com.revature.yolp.utils.custom_exceptions.ResourceConflictException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserRoleServlet extends HttpServlet {
    private final ObjectMapper mapper;
    private final UserRoleService userRoleService;

    public UserRoleServlet(ObjectMapper mapper, UserRoleService userRoleService) {
        this.mapper = mapper;
        this.userRoleService = userRoleService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            UserRoleRequest request = mapper.readValue(req.getInputStream(), UserRoleRequest.class);
            UserRole userRole = userRoleService.saveUserRole(request);
            resp.setContentType("application/json");
            resp.setStatus(200);
            resp.getWriter().write(mapper.writeValueAsString(userRole.getRole_id()));
            //resp.getWriter().write(mapper.writeValueAsString("Hello World!"));

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
            List<UserRole> roleList = new ArrayList<>();
            roleList = userRoleService.getAll();
            resp.setContentType("application/json");
            resp.setStatus(200);
            resp.getWriter().write(mapper.writeValueAsString(roleList));

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
