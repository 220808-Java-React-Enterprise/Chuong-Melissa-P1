package com.revature.yolp.dtos.requests;

public class UserRoleRequest {

    private String role;

    public UserRoleRequest() {

    }
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserRoleRequest{" +
                "role='" + role + '\'' +
                '}';
    }
}
