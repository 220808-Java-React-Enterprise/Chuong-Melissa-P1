package com.revature.strong.dtos.response;

public class Principal {
    private String id;
    private String username;
    private Boolean coach;

    public Principal(){

    }

    public Principal(String id, String username, Boolean coach) {
        this.id = id;
        this.username = username;
        this.coach = coach;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getCoach() {
        return coach;
    }

    public void setCoach(Boolean coach) {
        this.coach = coach;
    }

    @Override
    public String toString() {
        return "Principal{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", coach=" + coach +
                '}';
    }
}
