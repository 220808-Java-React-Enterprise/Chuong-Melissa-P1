package com.revature.yolp.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.yolp.daos.UserRoleDAO;
import com.revature.yolp.dtos.requests.UserRoleRequest;
import com.revature.yolp.models.User;
import com.revature.yolp.models.UserRole;
import com.revature.yolp.servlets.UserRoleServlet;

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


    public void update(UserRole obj) {

    }


    public void delete(String id) {

    }


    public UserRole getById(String id) {
        return null;
    }


    public List<UserRole> getAll() {
        return null;
    }
}
