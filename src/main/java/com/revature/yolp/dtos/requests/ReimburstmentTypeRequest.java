package com.revature.yolp.dtos.requests;

public class ReimburstmentTypeRequest {

    private String type_id;
    private String type;

    public ReimburstmentTypeRequest() {

    }

    public ReimburstmentTypeRequest(String type) {
        this.type = type;
    }

    public ReimburstmentTypeRequest(String type_id, String type) {
        this.type_id = type_id;
        this.type = type;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
