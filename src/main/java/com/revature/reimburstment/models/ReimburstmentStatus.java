package com.revature.reimburstment.models;

public class ReimburstmentStatus {
    private String stastus_id;
    private String status;

    public ReimburstmentStatus() {

    }

    public ReimburstmentStatus(String stastus_id, String status) {
        this.stastus_id = stastus_id;
        this.status = status;
    }

    public String getStastus_id() {
        return stastus_id;
    }

    public void setStastus_id(String stastus_id) {
        this.stastus_id = stastus_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ReimburstmentStatus{" +
                "stastus_id='" + stastus_id + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}// end class
