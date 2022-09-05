package com.revature.reimburstment.models;

import java.io.InputStream;
import java.math.BigDecimal;

public class Testing {

    private BigDecimal amount;

    private String description;

    private byte[] inputStream;

    private String type;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getInputStream() {
        return inputStream;
    }

    public void setInputStream(byte[] inputStream) {
        this.inputStream = inputStream;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Testing{" +
                "amount=" + amount +
                ", description='" + description + '\'' +
                ", inputStream=" + inputStream +
                ", type='" + type + '\'' +
                '}';
    }
}
