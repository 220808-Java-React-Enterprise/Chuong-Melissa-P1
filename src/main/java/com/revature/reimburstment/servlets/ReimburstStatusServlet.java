package com.revature.reimburstment.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.reimburstment.dtos.requests.ReimburstmentStatusRequest;
import com.revature.reimburstment.dtos.requests.UserRoleRequest;
import com.revature.reimburstment.dtos.responses.Principal;
import com.revature.reimburstment.models.ReimburstmentStatus;
import com.revature.reimburstment.models.UserRole;
import com.revature.reimburstment.services.RoleService;
import com.revature.reimburstment.services.TokenService;
import com.revature.reimburstment.services.ReimburstStatusService;
import com.revature.reimburstment.utils.custom_exceptions.InvalidRequestException;
import com.revature.reimburstment.utils.custom_exceptions.ResourceConflictException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ReimburstStatusServlet extends HttpServlet {
    private final ObjectMapper mapper;

    private TokenService tokenService;
    private final ReimburstStatusService reimburstStatusService;

    private final RoleService userRoleService;

    public ReimburstStatusServlet(ObjectMapper mapper,
                                  TokenService tokenService,
                                  ReimburstStatusService userReimburstStatusService,
                                  RoleService roleService) {
        this.mapper = mapper;
        this.reimburstStatusService = userReimburstStatusService;
        this.tokenService = tokenService;
        this.userRoleService = roleService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String role = getRole(req, resp);
            if (role.equals("ADMIN")) {
                ReimburstmentStatusRequest request =
                        mapper.readValue(req.getInputStream(), ReimburstmentStatusRequest.class);
                ReimburstmentStatus reimburstStatus = reimburstStatusService.saveReimburstmentStatus(request);
                resp.setContentType("application/json");
                resp.setStatus(200);
                resp.getWriter().write(mapper.writeValueAsString(reimburstStatus.getStastus_id()));
            } else {
                resp.setContentType("application/json");
                resp.getWriter().write(mapper.writeValueAsString(role));
            }

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
                List<ReimburstmentStatus> statusList = reimburstStatusService.getAll();
                resp.setContentType("application/json");
                resp.getWriter().write(mapper.writeValueAsString(statusList));
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
            UserRole userRole = this.userRoleService.getById(roleId);

            return userRole.getRole();
        }catch (NullPointerException e) {

        }catch (Exception e) {

        }
        return null;
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String role = getRole(req, resp);
            if (role.equals("ADMIN")) {
                ReimburstmentStatusRequest request = mapper.readValue(req.getInputStream(), ReimburstmentStatusRequest.class);
                this.reimburstStatusService.update(request);
                resp.setContentType("application/json");
                resp.setStatus(200);
                resp.getWriter().write(mapper.writeValueAsString("Updated " + request.getStatus_id()));
            }

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
            String role = getRole(req, resp);
            if (role.equals("ADMIN")) {
                ReimburstmentStatusRequest request = mapper.readValue(req.getInputStream(), ReimburstmentStatusRequest.class);
                reimburstStatusService.delete(request.getStatus_id());
                resp.setContentType("application/json");
                resp.setStatus(200);
                resp.getWriter().write(mapper.writeValueAsString("Deleted " + request.getStatus()));
            }

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
