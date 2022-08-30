package com.revature.strong.models;

import java.math.BigDecimal;

public class Supply {

    private String id;
    private String eqName;
    private BigDecimal quantity;
    private String store_id;

    public Supply(String id, String eqName, BigDecimal quantity, String store_id) {
        this.id = id;
        this.eqName = eqName;
        this.quantity = quantity;
        this.store_id = store_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEqName() {
        return eqName;
    }

    public void setEqName(String eqName) {
        this.eqName = eqName;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    @Override
    public String toString() {
        return "Supply{" +
                "id='" + id + '\'' +
                ", eqName='" + eqName + '\'' +
                ", quantity=" + quantity +
                ", store_id='" + store_id + '\'' +
                '}';
    }
}


