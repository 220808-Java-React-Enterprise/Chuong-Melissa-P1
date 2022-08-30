package com.revature.yolp.dtos.requests;

public class ReimburstmentTypeRequest {

    private String type_id;

    public ReimburstmentTypeRequest() {

    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    @Override
    public String toString() {
        return "ReimburstmentTypeRequest{" +
                "type_id='" + type_id + '\'' +
                '}';
    }
}
