package com.revature.reimburstment.dtos.requests;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ReimburstRequest {
    private BigDecimal amount;

    private String description;

    private String author_id;

    private String type;

    public ReimburstRequest() {

    }

    public ReimburstRequest(BigDecimal amount, String description, InputStream receipt, String type) {
        this.amount = amount;
        this.description = description;
        this.type = type;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ReimburstRequest{" +
                "amount=" + amount +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
