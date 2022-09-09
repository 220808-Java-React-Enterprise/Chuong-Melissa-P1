package com.revature.reimburstment.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.reimburstment.daos.ReimburstStatusDAO;
import com.revature.reimburstment.dtos.requests.ReimburstmentStatusRequest;
import com.revature.reimburstment.dtos.requests.UserRoleRequest;
import com.revature.reimburstment.models.ReimburstmentStatus;
import com.revature.reimburstment.models.UserRole;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class ReimburstStatusService {
    private ReimburstStatusDAO reimburstStatusDAO;

    public ReimburstStatusService(ReimburstStatusDAO userReimburstStatusDAO) {
        this.reimburstStatusDAO = userReimburstStatusDAO;
    }

    public ReimburstmentStatus saveReimburstmentStatus(ReimburstmentStatusRequest request) throws IOException {
        ReimburstmentStatus reimburstmentStatus = new ReimburstmentStatus(UUID.randomUUID().toString(), request.getStatus());
        reimburstStatusDAO.save(reimburstmentStatus);
        return reimburstmentStatus;
    }


    public boolean update(ReimburstmentStatusRequest request) {
        ReimburstmentStatus reimburstmentStatus = new ReimburstmentStatus(request.getStatus_id(), request.getStatus());
        int result = reimburstStatusDAO.update(reimburstmentStatus);

        if(result >= 1) {
            return true;
        }

        return false;
    }


    public void delete(String id) {
        reimburstStatusDAO.delete(id);
    }


    public ReimburstmentStatus getById(String id) {
        return reimburstStatusDAO.getById(id);
    }


    public List<ReimburstmentStatus> getAll() {
        return reimburstStatusDAO.getAll();
    }
}
