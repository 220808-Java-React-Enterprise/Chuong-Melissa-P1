package com.revature.yolp.dtos.responses;

public class Principal {
    private String user_id;
    private String username;
    private String role_id;

    public Principal() {

    }

    public Principal(String user_id, String username, String role_id) {
        this.user_id = user_id;
        this.username = username;
        this.role_id = role_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }
}
