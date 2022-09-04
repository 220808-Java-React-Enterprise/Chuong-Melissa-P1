package com.revature.reimburstment.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.reimburstment.dtos.requests.NewUserRequest;
import com.revature.reimburstment.dtos.responses.Principal;
import com.revature.reimburstment.models.User;
import com.revature.reimburstment.models.UserRole;
import com.revature.reimburstment.services.TokenService;
import com.revature.reimburstment.services.RoleService;
import com.revature.reimburstment.services.UserService;
import com.revature.reimburstment.utils.custom_exceptions.InvalidRequestException;
import com.revature.reimburstment.utils.custom_exceptions.ResourceConflictException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class UserServlet extends HttpServlet  {

    private final ObjectMapper mapper;

    private TokenService tokenService;
    private final UserService userService;

    private RoleService userRoleService;

    public UserServlet(ObjectMapper mapper, TokenService tokenService, UserService userService, RoleService userRoleService) {
        this.mapper = mapper;
        this.tokenService = tokenService;
        this.userService = userService;
        this.userRoleService = userRoleService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            //String role = getRole(req, resp);
            String role = getRoleWithSession(req);
            if (role.equals("ADMIN")) {
                List<User> userList = userService.getAllUsers();
                resp.setContentType("application/json");
                resp.getWriter().write(mapper.writeValueAsString(userList));
            } else {
                resp.setContentType("application/json");
                resp.setStatus(403);
                resp.getWriter().write(mapper.writeValueAsString("Invalid Credential"));
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
            throw new InvalidRequestException("Invalid Credential");
        }catch (Exception e) {
            throw new InvalidRequestException("Invalid Credential");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            //String role = getRole(req, resp);
            String role = getRoleWithSession(req);
            if (role.equals("ADMIN")) {
                NewUserRequest request = mapper.readValue(req.getInputStream(), NewUserRequest.class);
                User createdUser = userService.register(request);
                resp.setContentType("application/json");
                resp.setStatus(200);
                resp.getWriter().write(mapper.writeValueAsString("User added"));
            } else {
                resp.setStatus(403);
                resp.getWriter().write(mapper.writeValueAsString("Invalid Credential"));
            }
        } catch(InvalidRequestException e){
            resp.setStatus(403);
            resp.getWriter().write(mapper.writeValueAsString(e.getMessage()));
        } catch(ResourceConflictException e){
            resp.setStatus(409);
            resp.getWriter().write(mapper.writeValueAsString(e.getMessage()));
        } catch(Exception e){
            resp.setStatus(404);
            resp.getWriter().write(mapper.writeValueAsString(e.getMessage()));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("in doPut");
        try {
            //String role = getRole(req, resp);
            String role = getRoleWithSession(req);
            if (role.equals("ADMIN")) {
                System.out.println("inside admin");
                User request = mapper.readValue(req.getInputStream(), User.class);
                System.out.println("user id: " + request.getUser_id());
                userService.update(request);
                resp.setContentType("application/json");
                resp.setStatus(200);
                resp.getWriter().write(mapper.writeValueAsString("User updated"));
            } else {
                resp.setStatus(403);
                resp.getWriter().write(mapper.writeValueAsString("Invalid Credential"));
            }
        } catch(InvalidRequestException e){
            resp.setStatus(403);
            resp.getWriter().write(mapper.writeValueAsString("Invalid Credential"));
        } catch(ResourceConflictException e){
            resp.setStatus(409);
        } catch(Exception e){
            resp.setStatus(404);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            //String role = getRole(req, resp);
            String role = getRoleWithSession(req);
            if (role.equals("ADMIN")) {
                User request = mapper.readValue(req.getInputStream(), User.class);
                userService.delete(request.getUser_id());
                resp.setContentType("application/json");
                resp.setStatus(200);
                resp.getWriter().write(mapper.writeValueAsString("User deleted"));
            } else {
                resp.setStatus(403);
                resp.getWriter().write(mapper.writeValueAsString("Invalid Credential"));
            }
        } catch(InvalidRequestException e){
            resp.setStatus(403);
            resp.getWriter().write(mapper.writeValueAsString(e.getMessage()));
        } catch(ResourceConflictException e){
            resp.setStatus(409);
            resp.getWriter().write(mapper.writeValueAsString(e.getMessage()));
        } catch(Exception e){
            resp.setStatus(404);
            resp.getWriter().write(mapper.writeValueAsString(e.getMessage()));
        }
    }
}
