package com.revature.reimburstment.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.reimburstment.daos.RoleDAO;
import com.revature.reimburstment.dtos.requests.UserRoleRequest;
import com.revature.reimburstment.models.UserRole;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class RoleService {

    private ObjectMapper mapper = new ObjectMapper();
    private RoleDAO userRoleDAO;

    public RoleService(RoleDAO userRoleDAO) {
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
        return userRoleDAO.getById(id);
    }

    public UserRole getByRole(String role) {
        return userRoleDAO.getByRole(role);
    }


    public List<UserRole> getAll() {
        return userRoleDAO.getAll();
    }
}
