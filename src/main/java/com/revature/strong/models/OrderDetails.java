package com.revature.strong.models;

import java.math.BigDecimal;
import java.util.Date;

public class OrderDetails {

    private String id;
    private String userid;
    private Date oddate;
    private String equipment_id;
    private String eqname;
    private BigDecimal quantity;
    private BigDecimal subtotal;

    public OrderDetails(String id, String userid, Date oddate, String equipment_id, String eqname, BigDecimal quantity, BigDecimal subtotal) {
        this.id = id;
        this.userid = userid;
        this.equipment_id = equipment_id;
        this.eqname = eqname;
        this.quantity = quantity;
        this.subtotal = subtotal;
        this.oddate = oddate;
    }

    public Date getOddate() {
        return oddate;
    }

    public void setOddate(Date oddate) {
        this.oddate = oddate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getEquipment_id() {
        return equipment_id;
    }

    public void setEquipment_id(String equipment_id) {
        this.equipment_id = equipment_id;
    }

    public String getEqname() {
        return eqname;
    }

    public void setEqname(String eqname) {
        this.eqname = eqname;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "id='" + id + '\'' +
                ", userid='" + userid + '\'' +
                ", oddate=" + oddate +
                ", equipment_id='" + equipment_id + '\'' +
                ", eqname='" + eqname + '\'' +
                ", quantity=" + quantity +
                ", subtotal=" + subtotal +
                '}';
    }
}
