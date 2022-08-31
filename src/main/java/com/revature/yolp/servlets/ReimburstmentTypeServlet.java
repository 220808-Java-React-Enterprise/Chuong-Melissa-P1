package com.revature.yolp.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.yolp.dtos.requests.ReimburstmentTypeRequest;
import com.revature.yolp.dtos.requests.UserRoleRequest;
import com.revature.yolp.models.ReimburstmentType;
import com.revature.yolp.services.ReimburstmentTypeService;
import com.revature.yolp.utils.custom_exceptions.InvalidRequestException;
import com.revature.yolp.utils.custom_exceptions.ResourceConflictException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ReimburstmentTypeServlet extends HttpServlet {
    private final ObjectMapper mapper;
    private final ReimburstmentTypeService reimburstmentTypeService;

    public ReimburstmentTypeServlet(ObjectMapper mapper, ReimburstmentTypeService reimburstmentTypeService) {
        this.mapper = mapper;
        this.reimburstmentTypeService = reimburstmentTypeService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ReimburstmentTypeRequest request = mapper.readValue(req.getInputStream(), ReimburstmentTypeRequest.class);
            ReimburstmentType reimbustmentType = reimburstmentTypeService.saveReimburstType(request);
            resp.setContentType("application/json");
            resp.setStatus(200);
            resp.getWriter().write(mapper.writeValueAsString("Type : " + request.getType()));

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
//        resp.setStatus(200);
//        resp.getWriter().write(mapper.writeValueAsString("Get All Reimbustment Types"));

        try {
            List<ReimburstmentType> types = reimburstmentTypeService.getAll();
            System.out.println(types);
            resp.setContentType("application/json");
            resp.setStatus(200);
            resp.getWriter().write(mapper.writeValueAsString(types));

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
            ReimburstmentTypeRequest request = mapper.readValue(req.getInputStream(), ReimburstmentTypeRequest.class);
            reimburstmentTypeService.update(request);
            resp.setContentType("application/json");
            resp.setStatus(200);
            resp.getWriter().write(mapper.writeValueAsString("Updated " + request.getType_id()));

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
            ReimburstmentTypeRequest request = mapper.readValue(req.getInputStream(), ReimburstmentTypeRequest.class);
            reimburstmentTypeService.delete(request.getType());
            resp.setContentType("application/json");
            resp.setStatus(200);
            resp.getWriter().write(mapper.writeValueAsString("Deleted " + request.getType()));

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
