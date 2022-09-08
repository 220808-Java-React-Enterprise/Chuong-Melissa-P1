package com.revature.reimburstment.services;

import com.revature.reimburstment.daos.*;
import com.revature.reimburstment.dtos.requests.ReimUpdateRequest;
import com.revature.reimburstment.dtos.requests.ReimburstRequest;
import com.revature.reimburstment.dtos.requests.ReimburstmentFullRequest;
import com.revature.reimburstment.models.*;
import com.revature.reimburstment.utils.custom_exceptions.InvalidTypeException;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReimburstService {

    private final ReimburstDAO reimburstDAO;

    private final ReimburstmentTypeDAO reimburstmentTypeDAO;

    private final RoleDAO roleDAO;

    private final UserDAO userDAO;

    private final ReimburstStatusDAO reimburstStatusDAO;


    public ReimburstService(ReimburstDAO reimburstDAO, ReimburstmentTypeDAO reimburstmentTypeDAO) {
        this.reimburstDAO = reimburstDAO;
        this.reimburstmentTypeDAO = reimburstmentTypeDAO;
        roleDAO = new RoleDAO();
        userDAO = new UserDAO();
        reimburstStatusDAO = new ReimburstStatusDAO();

    }

    public List<ReimburstmentFullRequest> getAllReimburstForRequest() {
        List<ReimburstmentFullRequest> list = reimburstDAO.getAllReimburstForRequest();

        return list;
    }

    public List<ReimburstmentFullRequest> getAllReimburstById(String userId) {
        List<ReimburstmentFullRequest> list = reimburstDAO.getAllReimburstById(userId);
        return list;
    }

    public List<ReimburstmentFullRequest> getAllReimburstForRequest(String searchType) {
        List<ReimburstmentFullRequest> list = reimburstDAO.getAllReimburstForRequest(searchType);

        return list;
    }

    public void update(ReimburstRequest request) {
        Reimburstment reimburstment = new Reimburstment();
        reimburstment.setReimb_id(request.getReimb_id());
        reimburstment.setPayment_id(request.getPayment_id());
        reimburstment.setResolver_id(request.getResolver_id());

        ReimburstmentStatus reimburstmentStatus = this.reimburstStatusDAO.getByStatus(request.getStatus());

        reimburstment.setStatus_id(reimburstmentStatus.getStastus_id());
        reimburstDAO.update(reimburstment);
    }

    public void updateEmployeeReimburstment(ReimUpdateRequest request) {
        Reimburstment reimburstment = new ReimburstDAO().getById(request.getReimb_id());

        System.out.println("service return from ADO: " + reimburstment);

        ReimburstmentStatus status = new ReimburstStatusDAO().getById(reimburstment.getStatus_id());
        ReimburstmentType rt = new ReimburstmentTypeDAO().getByType(request.getType());

        System.out.println("reim:" + reimburstment);
        System.out.println("status:" + status);
        System.out.println("type:" + rt);

        if(status.getStatus().equals("PENDING")) {
            reimburstment.setAmount(request.getAmount());
            reimburstment.setDescription(request.getDescription());
            reimburstment.setType_id(rt.getType_id());
            System.out.println("reimburstment: " + reimburstment);

            System.out.println("init usr request before sending to ADO: " + reimburstment);
            reimburstDAO.updateEmployeeReimburstment(reimburstment);

        } else {
            throw new InvalidTypeException("Reimburstment is already processed.");
        }
    }


    public Reimburstment createReimburst(ReimburstRequest request) throws IOException {
        Reimburstment reimburstment = new Reimburstment();
        ReimburstmentType type = reimburstmentTypeDAO.getByType(request.getType());
        UserRole userRole = roleDAO.getByRole("FINANCE");

        User resolver = userDAO.getUserByRoleId(userRole.getRole_id());

        ReimburstmentStatus reimburstmentStatus = reimburstDAO.getByStatus("PENDING");

        reimburstment.setReimb_id(UUID.randomUUID().toString());
        reimburstment.setAmount(request.getAmount());
        reimburstment.setSubmitted(Timestamp.valueOf(LocalDateTime.now()));
        reimburstment.setResolved(null);
        reimburstment.setDescription(request.getDescription());
        reimburstment.setReceipt(request.getReceipt());
        reimburstment.setPayment_id(null);
        reimburstment.setAuthor_id(request.getAuthor_id());

        reimburstment.setResolver_id(null);
        reimburstment.setStatus_id(reimburstmentStatus.getStastus_id());
        reimburstment.setType_id(type.getType_id());



        reimburstDAO.save(reimburstment);

        return reimburstment;
    }

    public void initializeResolver(String userId, ReimburstmentStatus status) {
        reimburstDAO.initializeResolver(userId, status);
    }

    public void getByStatus(String status) {
        reimburstDAO.getByStatus(status);
    }
}
