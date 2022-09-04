package com.revature.reimburstment.dtos.requests;

import java.math.BigDecimal;

import java.time.LocalDateTime;
import java.util.Date;

public class ReimburstmentFullRequest {

    private String reimb_id;

    private BigDecimal amount;

    private Date submitted;

    private String description;

    private  String payment_id;

    private String author_first_name;

    private String author_last_name;

    private String resolver_first_name;

    private String resolver_last_name;

    private String reimburst_type;

    private String  reimburst_status;

    public ReimburstmentFullRequest() {

    }

    public String getReimb_id() {
        return reimb_id;
    }

    public void setReimb_id(String reimb_id) {
        this.reimb_id = reimb_id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getSubmitted() {
        return submitted;
    }

    public void setSubmitted(Date submitted) {
        this.submitted = submitted;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public String getAuthor_first_name() {
        return author_first_name;
    }

    public void setAuthor_first_name(String author_first_name) {
        this.author_first_name = author_first_name;
    }

    public String getAuthor_last_name() {
        return author_last_name;
    }

    public void setAuthor_last_name(String author_last_name) {
        this.author_last_name = author_last_name;
    }

    public String getResolver_first_name() {
        return resolver_first_name;
    }

    public void setResolver_first_name(String resolver_first_name) {
        this.resolver_first_name = resolver_first_name;
    }

    public String getResolver_last_name() {
        return resolver_last_name;
    }

    public void setResolver_last_name(String resolver_last_name) {
        this.resolver_last_name = resolver_last_name;
    }

    public String getReimburst_type() {
        return reimburst_type;
    }

    public void setReimburst_type(String reimburst_type) {
        this.reimburst_type = reimburst_type;
    }

    public String getReimburst_status() {
        return reimburst_status;
    }

    public void setReimburst_status(String reimburst_status) {
        this.reimburst_status = reimburst_status;
    }

    @Override
    public String toString() {
        return "ReimburstmentFullRequest{" +
                "reimb_id='" + reimb_id + '\'' +
                ", amount=" + amount +
                ", submitted=" + submitted +
                ", description='" + description + '\'' +
                ", payment_id='" + payment_id + '\'' +
                ", author_first_name='" + author_first_name + '\'' +
                ", author_last_name='" + author_last_name + '\'' +
                ", resolver_first_name='" + resolver_first_name + '\'' +
                ", resolver_last_name='" + resolver_last_name + '\'' +
                ", reimburst_type='" + reimburst_type + '\'' +
                ", reimburst_status='" + reimburst_status + '\'' +
                '}';
    }
}
