package com.revature.reimburstment.services;

import com.revature.reimburstment.daos.ReimburstmentTypeDAO;
import com.revature.reimburstment.dtos.requests.ReimburstmentTypeRequest;
import com.revature.reimburstment.models.ReimburstmentType;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class ReimburstmentTypeService {
    private ReimburstmentTypeDAO reimburstmentTypeDAO;

    public ReimburstmentTypeService(ReimburstmentTypeDAO reimburstmentTypeDAO) {

        this.reimburstmentTypeDAO = reimburstmentTypeDAO;
    }

    public ReimburstmentType saveReimburstType(ReimburstmentTypeRequest request) throws IOException {


        ReimburstmentType reimburstmentType = new ReimburstmentType(UUID.randomUUID().toString(), request.getType());
        reimburstmentTypeDAO.save(reimburstmentType);
        return reimburstmentType;
    }


    public void update(ReimburstmentTypeRequest obj) {
        ReimburstmentType reimburstmentType = new ReimburstmentType(obj.getType_id(), obj.getType());
        reimburstmentTypeDAO.update(reimburstmentType);
    }


    public void delete(String id) {
        reimburstmentTypeDAO.delete(id);
    }


    public ReimburstmentType getById(String id) {
        return reimburstmentTypeDAO.getById(id);
    }


    public List<ReimburstmentType> getAll() {
        return reimburstmentTypeDAO.getAll();
    }
}
