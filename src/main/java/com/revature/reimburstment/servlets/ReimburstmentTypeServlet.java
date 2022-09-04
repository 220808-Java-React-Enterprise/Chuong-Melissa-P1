package com.revature.reimburstment.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.reimburstment.dtos.requests.ReimburstmentTypeRequest;
import com.revature.reimburstment.dtos.responses.Principal;
import com.revature.reimburstment.models.ReimburstmentType;
import com.revature.reimburstment.models.UserRole;
import com.revature.reimburstment.services.ReimburstmentTypeService;
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

public class ReimburstmentTypeServlet extends HttpServlet {
    private final ObjectMapper mapper;

    private final TokenService tokenService;
    private final ReimburstmentTypeService reimburstmentTypeService;

    private final RoleService userRoleService;

    public ReimburstmentTypeServlet(ObjectMapper mapper, TokenService tokenService,
                                    ReimburstmentTypeService reimburstmentTypeService,
                                    RoleService userRoleService) {
        this.mapper = mapper;
        this.reimburstmentTypeService = reimburstmentTypeService;
        this.tokenService = tokenService;
        this.userRoleService = userRoleService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            //String role = getRole(req, resp);
            String role = getRoleWithSession((req));
            if(role.equals("ADMIN")) {
                ReimburstmentTypeRequest request = mapper.readValue(req.getInputStream(), ReimburstmentTypeRequest.class);
                ReimburstmentType reimbustmentType = reimburstmentTypeService.saveReimburstType(request);
                resp.setContentType("application/json");
                resp.setStatus(200);
                resp.getWriter().write(mapper.writeValueAsString("Type : " + request.getType()));
            }else {
                resp.setStatus(409);
                resp.getWriter().write(mapper.writeValueAsString("Role : " + role));
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
            //String role = getRole(req, resp);
            String role = getRoleWithSession(req);

            if(role.equals("ADMIN")) {
                List<ReimburstmentType> types = reimburstmentTypeService.getAll();
                System.out.println(types);
                resp.setContentType("application/json");
                resp.setStatus(200);
                resp.getWriter().write(mapper.writeValueAsString(types));
            }else {
                resp.setStatus(403);
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
            if(role.equals("ADMIN")) {
                ReimburstmentTypeRequest request = mapper.readValue(req.getInputStream(), ReimburstmentTypeRequest.class);
                reimburstmentTypeService.update(request);
                resp.setContentType("application/json");
                resp.setStatus(200);
                resp.getWriter().write(mapper.writeValueAsString("Updated " + request.getType_id()));
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
            //String role = getRole(req, resp);
            String role = getRoleWithSession(req);
            if(role.equals("ADMIN")) {
                ReimburstmentTypeRequest request = mapper.readValue(req.getInputStream(), ReimburstmentTypeRequest.class);
                reimburstmentTypeService.delete(request.getType_id());
                resp.setContentType("application/json");
                resp.setStatus(200);
                resp.getWriter().write(mapper.writeValueAsString("Deleted TYPES"));
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
