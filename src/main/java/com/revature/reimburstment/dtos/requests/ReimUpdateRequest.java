package com.revature.reimburstment.dtos.requests;

import java.math.BigDecimal;
import java.util.Arrays;

public class ReimUpdateRequest {

    private String reimb_id;
    private byte[] receipt;
    private String description;
    private BigDecimal amount;

    private String type_id;

    private String type;

    public String getReimb_id() {
        return reimb_id;
    }

    public void setReimb_id(String reimb_id) {
        this.reimb_id = reimb_id;
    }

    public byte[] getReceipt() {
        return receipt;
    }

    public void setReceipt(byte[] receipt) {
        this.receipt = receipt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

    @Override
    public String toString() {
        return "ReimUpdateRequest{" +
                "reimb_id='" + reimb_id + '\'' +
                ", receipt=" + Arrays.toString(receipt) +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", type_id='" + type_id + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
