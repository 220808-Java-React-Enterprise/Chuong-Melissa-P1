package com.revature.reimburstment.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.reimburstment.daos.TestingDAO;

import com.revature.reimburstment.models.Testing;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;



public class TestServlet extends HttpServlet {
    private final ObjectMapper mapper;

    private final TestingDAO testingDAO;

    public TestServlet() {
        this.mapper = new ObjectMapper();
        this.testingDAO = new TestingDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Testing> list = this.testingDAO.getAll();
        Testing t = null;
        if(list.size() > 0) {
            t = list.get(0);
        }
        resp.setContentType("application/json");
        resp.getWriter().write(mapper.writeValueAsString(list));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ServletFileUpload sf = new ServletFileUpload(new DiskFileItemFactory());
        InputStream is = null;
        Testing t = null;
        try {
            List<FileItem> multifiles = sf.parseRequest(req);
            for(FileItem item : multifiles) {
                String itemFileName = item.getName();
                if(itemFileName != null) {
                    System.out.println("filename : " + itemFileName);
                    is = item.getInputStream();
                } else {
                    t = mapper.readValue(item.getInputStream(), Testing.class);
                    System.out.println("amount: " + t.getAmount());
                    System.out.println("description: " + t.getDescription());
                    System.out.println("type: " + t.getType());
                }
            }

            // testing

            byte[] bytes = IOUtils.toByteArray(is);

            t.setInputStream(bytes);

            new TestingDAO().save(t);


        } catch (FileUploadException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
