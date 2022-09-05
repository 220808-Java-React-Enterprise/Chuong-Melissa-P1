package com.revature.reimburstment.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.reimburstment.dtos.requests.UserRoleRequest;
import com.revature.reimburstment.dtos.responses.Principal;
import com.revature.reimburstment.models.UserRole;
import com.revature.reimburstment.services.TokenService;
import com.revature.reimburstment.services.RoleService;
import com.revature.reimburstment.utils.custom_exceptions.InvalidRequestException;
import com.revature.reimburstment.utils.custom_exceptions.ResourceConflictException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class UserRoleServlet extends HttpServlet {
    private final ObjectMapper mapper;

    private TokenService tokenService;
    private final RoleService userRoleService;

    public UserRoleServlet(ObjectMapper mapper, TokenService tokenService, RoleService userRoleService) {
        this.mapper = mapper;
        this.userRoleService = userRoleService;
        this.tokenService = tokenService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            //String role = getRole(req, resp);
            String role = getRoleWithSession(req);
            if (role.equals("ADMIN")) {
                UserRoleRequest request = mapper.readValue(req.getInputStream(), UserRoleRequest.class);
                UserRole userRole = userRoleService.saveUserRole(request);
                resp.setContentType("application/json");
                resp.setStatus(200);
                resp.getWriter().write(mapper.writeValueAsString(userRole));
            } else {
                resp.setContentType("application/json");
                resp.getWriter().write(mapper.writeValueAsString(role));
            }

        } catch (InvalidRequestException e) {
            resp.setStatus(404);
            resp.getWriter().write(mapper.writeValueAsString(e.getMessage()));
        } catch (ResourceConflictException e) {
            resp.setStatus(409);
            resp.getWriter().write(mapper.writeValueAsString(e.getMessage()));
        } catch (Exception e) {
            resp.setStatus(404);
            resp.getWriter().write(mapper.writeValueAsString(e.getMessage()));
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            //String role = getRole(req, resp);
            String role = getRoleWithSession(req);
            System.out.println("role is: " + role + "inside doGet() of get all user roles");
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
            resp.getWriter().write(mapper.writeValueAsString(e.getMessage()));
        }
    }

    // only ADMIN and FINANCE users are allowed to administer the system
    private String getRoleWithSession(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String token = (String) session.getAttribute("token");
        Principal principal = tokenService.extractRequesterDetails(token);
        String roleId = principal.getRole_id();
        UserRole userRole = userRoleService.getById(roleId);
        String role = userRole.getRole();

        return role;
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
            //String role = getRole(req, resp);
            String role = getRoleWithSession(req);
            if (role.equals("ADMIN")) {
                UserRoleRequest request = mapper.readValue(req.getInputStream(), UserRoleRequest.class);
                userRoleService.update(request);
                resp.setContentType("application/json");
                resp.setStatus(200);
                resp.getWriter().write(mapper.writeValueAsString("Updated " + request.getRole_id()));
            }

        } catch (InvalidRequestException e) {
            resp.setStatus(404);
            resp.getWriter().write(mapper.writeValueAsString(e.getMessage()));
        } catch (ResourceConflictException e) {
            resp.setStatus(409);
            resp.getWriter().write(mapper.writeValueAsString(e.getMessage()));
        } catch (Exception e) {
            resp.setStatus(404);
            resp.getWriter().write(mapper.writeValueAsString(e.getMessage()));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            //String role = getRole(req, resp);
            String role = getRoleWithSession(req);
            if (role.equals("ADMIN")) {
                UserRoleRequest request = mapper.readValue(req.getInputStream(), UserRoleRequest.class);
                userRoleService.delete(request.getRole_id());
                resp.setContentType("application/json");
                resp.setStatus(200);
                resp.getWriter().write(mapper.writeValueAsString("Deleted " + request.getRole()));
            }

        } catch (InvalidRequestException e) {
            resp.setStatus(404);
            resp.getWriter().write(mapper.writeValueAsString(e.getMessage()));
        } catch (ResourceConflictException e) {
            resp.setStatus(409);
            resp.getWriter().write(mapper.writeValueAsString(e.getMessage()));
        } catch (Exception e) {
            resp.setStatus(404);
            resp.getWriter().write(mapper.writeValueAsString(e.getMessage()));
        }
    }
}
