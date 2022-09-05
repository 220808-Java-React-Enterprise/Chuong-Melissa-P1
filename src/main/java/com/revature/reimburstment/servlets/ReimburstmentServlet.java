package com.revature.reimburstment.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.reimburstment.daos.ReimburstDAO;
import com.revature.reimburstment.daos.ReimburstStatusDAO;
import com.revature.reimburstment.daos.RoleDAO;
import com.revature.reimburstment.daos.TestingDAO;
import com.revature.reimburstment.dtos.requests.NewUserRequest;
import com.revature.reimburstment.dtos.requests.ReimburstRequest;
import com.revature.reimburstment.dtos.requests.ReimburstmentFullRequest;
import com.revature.reimburstment.dtos.responses.Principal;
import com.revature.reimburstment.models.*;
import com.revature.reimburstment.services.ReimburstService;
import com.revature.reimburstment.services.RoleService;
import com.revature.reimburstment.services.TokenService;
import com.revature.reimburstment.services.UserService;
import com.revature.reimburstment.utils.custom_exceptions.InvalidRequestException;
import com.revature.reimburstment.utils.custom_exceptions.ResourceConflictException;
import com.revature.reimburstment.utils.database.ConnectionFactory;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class ReimburstmentServlet extends HttpServlet {

    private final ObjectMapper mapper;

    private TokenService tokenService;

    private final ReimburstService reimburstService;

    private final RoleService roleService;

    private static final String SAVE_DIR = "uploadFiles";
    public ReimburstmentServlet(ObjectMapper mapper, TokenService tokenService, ReimburstService reimburstService) {
        this.mapper = mapper;
        this.tokenService = tokenService;
        this.reimburstService = reimburstService;
        roleService = new RoleService(new RoleDAO());
    }

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-diposition");
        String[] items = contentDisp.split(";");
        for(String s : items) {
            if(s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        return null;
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String role = getRoleWithSession(req);
//        resp.setStatus(200);
//        resp.getWriter().write(mapper.writeValueAsString("Welcome to doPut() Reim: " + role));
        try
        {
            //String role = getRole(req, resp);
            String role = getRoleWithSession(req);
            if (role.equals("FINANCE"))
            {
                ReimburstRequest request = mapper.readValue(req.getInputStream(), ReimburstRequest.class);
                String userId = getUserIdWithSession(req);
                request.setResolver_id(userId);
                reimburstService.update(request);
                resp.setContentType("application/json");
                resp.getWriter().write(mapper.writeValueAsString("Updated Reimburstment " + request.getReimb_id()));
            }

        } catch(InvalidRequestException e){
            resp.setStatus(403);
            resp.getWriter().write(mapper.writeValueAsString("Invalid Credential"));
        } catch(ResourceConflictException e){
            resp.setStatus(409);
        } catch(Exception e){
            resp.setStatus(404);
            resp.setContentType("application/json");
            resp.getWriter().write(mapper.writeValueAsString(e.getMessage()));
        }
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try
        {
            //String role = getRole(req, resp);
            String role = getRoleWithSession(req);
            if (role.equals("FINANCE"))
            {
                ReimburstRequest request = mapper.readValue(req.getInputStream(), ReimburstRequest.class);
                String userId = getUserIdWithSession(req);
                ReimburstmentStatus reimburstmentStatus = new ReimburstStatusDAO().getByStatus("PENDING");
                reimburstService.initializeResolver(userId, reimburstmentStatus);

                List<ReimburstmentFullRequest> reimList = null;
                String searchType = request.getSearch().toUpperCase();
                String searchStatus = request.getStatus().toUpperCase();

                if(!searchType.equals("ALL") && !searchStatus.equals("ALL")) {
                    reimList = reimburstService.getAllReimburstForRequest(searchType, searchStatus);
                }else {
                    reimList = reimburstService.getAllReimburstForRequest();
                }

                resp.setContentType("application/json");
                resp.getWriter().write(mapper.writeValueAsString(reimList));
            }

        } catch(InvalidRequestException e){
            resp.setStatus(403);
            resp.getWriter().write(mapper.writeValueAsString("Invalid Credential"));
        } catch(ResourceConflictException e){
            resp.setStatus(409);
        } catch(Exception e){
            resp.setStatus(404);
            resp.setContentType("application/json");
            resp.getWriter().write(mapper.writeValueAsString(e.getMessage()));
        }
    }

    private Principal getPricipal(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String token = req.getHeader("Authorization");
            Principal principal = tokenService.extractRequesterDetails(token);
            return principal;
        }catch (NullPointerException e) {

        }catch (Exception e) {

        }
        return null;
    }

    private String getUserId(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String token = req.getHeader("Authorization");
            Principal principal = tokenService.extractRequesterDetails(token);
            String userId = principal.getUser_id();
            return userId;
        }catch (NullPointerException e) {

        }catch (Exception e) {

        }
        return null;
    }

    // only ADMIN and FINANCE users are allowed to administer the system
    private String getRoleWithSession(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String token = (String) session.getAttribute("token");
        Principal principal = tokenService.extractRequesterDetails(token);
        String roleId = principal.getRole_id();
        UserRole userRole = roleService.getById(roleId);
        String role = userRole.getRole();

        return role;
    }

    private String getUserIdWithSession(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String token = (String) session.getAttribute("token");
        Principal principal = tokenService.extractRequesterDetails(token);

        return principal.getUser_id();
    }

    // only ADMIN and FINANCE users are allowed to administer the system
    private String getRole(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String token = req.getHeader("Authorization");
            Principal principal = tokenService.extractRequesterDetails(token);
            String roleId = principal.getRole_id();
            UserRole userRole = roleService.getById(roleId);

            return userRole.getRole();
        }catch (NullPointerException e) {
            throw new InvalidRequestException("Invalid Credential");
        }catch (Exception e) {
            throw new InvalidRequestException("Invalid Credential");
        }
    }


    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        try {
            String role = getRoleWithSession(req);
            if (role.equals("ADMIN") || role.equals("EMPLOYEE") || role.equals("FINANCE")) {
                ServletFileUpload sf = new ServletFileUpload(new DiskFileItemFactory());
                InputStream is = null;
                ReimburstRequest reimburstRequest = null;
                List<FileItem> multifiles = sf.parseRequest(req);
                for (FileItem item : multifiles) {
                    String itemFileName = item.getName();
                    if (itemFileName != null) {

                        is = item.getInputStream();
                    } else {
                        reimburstRequest = mapper.readValue(item.getInputStream(), ReimburstRequest.class);

                    }
                }

                byte[] bytes = IOUtils.toByteArray(is);

                reimburstRequest.setReceipt(bytes);
                String author_id = getUserIdWithSession(req);
                reimburstRequest.setAuthor_id(author_id);

                reimburstService.createReimburst(reimburstRequest);

                resp.setStatus(200);
                resp.getWriter().write(mapper.writeValueAsString("record saved"));

            }// end if

        } catch (FileUploadException e) {
            throw new RuntimeException(e);
        } catch(InvalidRequestException e){
            resp.setStatus(403);
            resp.getWriter().write(mapper.writeValueAsString("Invalid Credential"));
        } catch(ResourceConflictException e){
            resp.setStatus(409);
            resp.getWriter().write(mapper.writeValueAsString(e.getMessage()));
        } catch(Exception e){
            resp.setStatus(404);
            resp.getWriter().write(mapper.writeValueAsString(e.getMessage()));
        }
    }
}
