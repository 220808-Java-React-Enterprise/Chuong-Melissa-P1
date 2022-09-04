package com.revature.reimburstment.services;

import com.revature.reimburstment.daos.ReimburstDAO;
import com.revature.reimburstment.daos.ReimburstmentTypeDAO;
import com.revature.reimburstment.daos.RoleDAO;
import com.revature.reimburstment.daos.UserDAO;
import com.revature.reimburstment.dtos.requests.ReimburstRequest;
import com.revature.reimburstment.models.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

public class ReimburstService {

    private final ReimburstDAO reimburstDAO;

    private final ReimburstmentTypeDAO reimburstmentTypeDAO;

    private final RoleDAO roleDAO;

    private final UserDAO userDAO;

    public ReimburstService(ReimburstDAO reimburstDAO, ReimburstmentTypeDAO reimburstmentTypeDAO) {
        this.reimburstDAO = reimburstDAO;
        this.reimburstmentTypeDAO = reimburstmentTypeDAO;
        roleDAO = new RoleDAO();
        userDAO = new UserDAO();
    }


    public Reimburstment createReimburst(ReimburstRequest request) throws IOException {
        Reimburstment reimburstment = new Reimburstment();
        ReimburstmentType type = reimburstmentTypeDAO.getByType(request.getType());
        UserRole userRole = roleDAO.getByRole("FINANCE");
        System.out.println("createReimburst() " + userRole);
        System.out.println("createReimburst() role: " + userRole.getRole());
        User resolver = userDAO.getUserByRoleId(userRole.getRole_id());
        System.out.println("createReimburst() resolver: " + resolver);
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
