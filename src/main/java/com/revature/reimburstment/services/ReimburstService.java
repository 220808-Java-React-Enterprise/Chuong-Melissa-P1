package com.revature.reimburstment.services;

import com.revature.reimburstment.daos.*;
import com.revature.reimburstment.dtos.requests.ReimburstRequest;
import com.revature.reimburstment.dtos.requests.ReimburstmentFullRequest;
import com.revature.reimburstment.models.*;

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
        System.out.println("\n" + list + "\n");

        return list;
    }

    public void update(ReimburstRequest request) {
        Reimburstment reimburstment = new Reimburstment();
        reimburstment.setReimb_id(request.getReimb_id());
        reimburstment.setPayment_id(request.getPayment_id());
        reimburstment.setStatus_id(request.getStatus_id());
        reimburstDAO.update(reimburstment);
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
        reimburstment.setDescription(request.getDescription());
        reimburstment.setPayment_id(UUID.randomUUID().toString());
        reimburstment.setAuthor_id(request.getAuthor_id());
        reimburstment.setResolver_id(resolver.getUser_id());
        reimburstment.setStatus_id(reimburstmentStatus.getStastus_id());
        reimburstment.setType_id(type.getType_id());



        reimburstDAO.save(reimburstment);

        return reimburstment;
    }



}
