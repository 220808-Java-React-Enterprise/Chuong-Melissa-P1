package com.revature.strong.models;

public class User {
    private String username;
    private String userpassword;
    private String id;
    private Boolean coach;
    private String coach_id;

    public User(){

    }

    public User(String username, String userpassword, String id, Boolean coach){
        this.username = username;
        this.userpassword = userpassword;
        this.id = id;
        this.coach = coach;
    }

    public User(String username, String userpassword, String id, Boolean coach, String coach_id) {
        this.username = username;
        this.userpassword = userpassword;
        this.id = id;
        this.coach = coach;
        this.coach_id = coach_id;
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

    public String getUserPassword() {
        return userpassword;
    }

    public void setUserPassword(String password) {
        this.userpassword = userpassword;
    }

    public Boolean getCoach() {
        return coach;
    }

    public void setCoach(Boolean coach) {
        this.coach = coach;
    }

    public String getCoach_id() {
        return coach_id;
    }

    public void setCoach_id(String coach_id) {
        this.coach_id = coach_id;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", userpassword='" + userpassword + '\'' +
                ", id='" + id + '\'' +
                ", coach=" + coach +
                ", coach_id='" + coach_id + '\'' +
                '}';
    }
}
