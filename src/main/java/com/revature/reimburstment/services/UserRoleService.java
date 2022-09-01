package com.revature.reimburstment.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.reimburstment.daos.UserRoleDAO;
import com.revature.reimburstment.dtos.requests.UserRoleRequest;
import com.revature.reimburstment.models.UserRole;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class UserRoleService {

    private ObjectMapper mapper = new ObjectMapper();
    private UserRoleDAO userRoleDAO;

    public UserRoleService(UserRoleDAO userRoleDAO) {
        this.userRoleDAO = userRoleDAO;
    }

    public UserRole saveUserRole(UserRoleRequest request) throws IOException {
        UserRole userRole = new UserRole(UUID.randomUUID().toString(), request.getRole());
        userRoleDAO.save(userRole);
        return userRole;
    }


    public void update(UserRoleRequest request) {
        UserRole userRole = new UserRole(request.getRole_id(), request.getRole());
        userRoleDAO.update(userRole);
    }


    public void delete(String id) {
        userRoleDAO.delete(id);
    }


    public UserRole getById(String id) {
        return null;
    }


    public List<UserRole> getAll() {
        return userRoleDAO.getAll();
    }
}
