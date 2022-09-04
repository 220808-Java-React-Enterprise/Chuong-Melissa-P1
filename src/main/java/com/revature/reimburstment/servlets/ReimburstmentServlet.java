package com.revature.reimburstment.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.reimburstment.daos.RoleDAO;
import com.revature.reimburstment.dtos.requests.NewUserRequest;
import com.revature.reimburstment.dtos.requests.ReimburstRequest;
import com.revature.reimburstment.dtos.responses.Principal;
import com.revature.reimburstment.models.Reimburstment;
import com.revature.reimburstment.models.User;
import com.revature.reimburstment.models.UserRole;
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
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write(mapper.writeValueAsString("got to doGet()"));
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


    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("inside do post");
        ReimburstRequest reimburstRequest = mapper.readValue(req.getInputStream(), ReimburstRequest.class);

        String userId = getUserId(req, resp);

        reimburstRequest.setAuthor_id(userId);
        System.out.println("user that login id: " + reimburstRequest.getAuthor_id());

        Reimburstment reimburstment = reimburstService.createReimburst(reimburstRequest);

        System.out.println(reimburstRequest.getDescription());
        resp.setContentType("application/text");
        resp.getWriter().write(mapper.writeValueAsString(reimburstment));

//        String appPath = req.getServletContext().getRealPath("");
//        System.out.println(appPath);
//        String savePath = appPath + File.separator + SAVE_DIR;
//
//        ServletFileUpload sf = new ServletFileUpload(new DiskFileItemFactory());
//        InputStream inputStream = null;
//        ReimburstRequest reimburstRequest = null;
//        try {
//            List<FileItem> multifiles = sf.parseRequest(req);
//            for(FileItem item : multifiles) {
//                String itemFileName = item.getName();
//                if(itemFileName != null) {
//                    System.out.println("filename : " + itemFileName);
//                    inputStream = item.getInputStream();
//                } else {
//                    reimburstRequest = mapper.readValue(item.getInputStream(), ReimburstRequest.class);
//                    System.out.println("amount: " + reimburstRequest.getAmount());
//                    System.out.println("discription: " + reimburstRequest.getDescription());
//
//                }
//
//            }
//            //reimburstRequest.setReceipt(inputStream);
//
//        } catch (FileUploadException e) {
//            throw new RuntimeException(e);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }


//        String contentDisposition = part.getHeader("content-disposition");
//
//        System.out.println(contentDisposition);



        //String imageFileName = file.getName();
//
//        System.out.println(imageFileName);




//        ServletFileUpload sf = new ServletFileUpload(new DiskFileItemFactory());
//        String filePath = System.getProperty("user.dir");
//        FileInputStream fis = null;
//        InputStream inputStream = null;
//        byte[] bytes;
//        try {
//            List<FileItem> fileItems = sf.parseRequest(req);
//            for(FileItem item : fileItems) {
//                //item.write(new File(filePath + "/" + item.getName()));
//                System.out.println(item.getName());
//                inputStream = item.getInputStream();
//                //fis = new FileInputStream(String.valueOf(inputStream));
//
//            }
//            Connection con = ConnectionFactory.getInstance().getConnection();
//
//            String sql =
//                    "insert into ers_reimbursements (reimb_id, amount, submitted, resolved, description, receipt, payment_id, author_id, resolver_id, status_id, type_id) values(?, ? , ?, ?, ?, ?, ?, ? , ?, ?, ?)";
//            PreparedStatement ps = con.prepareStatement(sql); // id
//            ps.setString(1, UUID.randomUUID().toString()); /// id
//            ps.setBigDecimal(2, BigDecimal.valueOf(300.35)); // amoount
//            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now())); // submitted
//            ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now())); // resolved
//            ps.setString(5, "this is a receipt"); // description
//            ps.setBinaryStream(6, inputStream); // receipt
//            ps.setString(7, "123"); // payment_id
//            ps.setString(8, "123"); // author_id
//            ps.setString(9, "123"); // resolver_id
//            ps.setString(10, "123"); // status_id
//            ps.setString(11, "123");
//            ps.executeUpdate();
//
//            System.out.println("file uploaded");
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        try {
//            ReimburstRequest request = mapper.readValue(req.getInputStream(), ReimburstRequest.class);
//            Reimburstment createdReimburst = reimburstService.createReimburst(request);
//            resp.setContentType("application/json");
//            resp.setStatus(200);
//            resp.getWriter().write(mapper.writeValueAsString(createdReimburst.getReimb_id()));
//
//        } catch (InvalidRequestException e) {
//            resp.setStatus(404);
//            resp.getWriter().write(mapper.writeValueAsString(e.getMessage()));
//        } catch (ResourceConflictException e) {
//            resp.setStatus(409);
//        } catch (Exception e) {
//            resp.setStatus(404);
//        }
    }


}
