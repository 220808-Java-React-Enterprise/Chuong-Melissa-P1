package com.revature.strong.daos;

import com.revature.strong.models.OrderDetails;
import com.revature.strong.utils.custom_exceptions.InvalidSQLException;
import com.revature.strong.utils.database.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDetailDAO implements CrudDAO<OrderDetails> {


    @Override
    public void save(OrderDetails obj) {
        long millis = System.currentTimeMillis();
        Date today = new Date(millis);
        java.sql.Date sqlDate = new java.sql.Date(today.getTime());
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO orderdetails (id, user_id, oddate, equipment_id, eqname, quantity, subtotal) VALUES (?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, obj.getId());
            ps.setString(2, obj.getUserid());
            ps.setDate(3, sqlDate);
            ps.setString(4, obj.getEquipment_id());
            ps.setString(5, obj.getEqname());
            ps.setBigDecimal(6, obj.getQuantity());
            ps.setBigDecimal(7, obj.getSubtotal());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new InvalidSQLException("An error occurred when trying to save to the database");
        }
    }

    @Override
    public void update(OrderDetails obj) {

    }

    @Override
    public void delete(OrderDetails obj) {

    }

    @Override
    public OrderDetails getById(String id) {
        return null;
    }

    @Override
    public List<OrderDetails> getAll() {
        return null;
    }

    public List<OrderDetails> getAllById(String id){
        List<OrderDetails> orders = new ArrayList<>();

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM orderdetails WHERE user_id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                OrderDetails ord = new OrderDetails(rs.getString("id"), rs.getString("user_id"), rs.getDate("oddate"), rs.getString("equipment_id"), rs.getString("eqname"), rs.getBigDecimal("quantity"), rs.getBigDecimal("subtotal"));
                orders.add(ord);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new InvalidSQLException("An error occurred when trying to retrieve from the database");
        }

        return orders;
    }

    public List<OrderDetails> sortOrdersByPrice(String userid){
        List<OrderDetails> orders = new ArrayList<>();

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM orderdetails WHERE user_id = ? ORDER BY subtotal");
            ps.setString(1, userid);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                OrderDetails ord = new OrderDetails(rs.getString("id"), rs.getString("user_id"), rs.getDate("oddate"), rs.getString("equipment_id"), rs.getString("eqname"), rs.getBigDecimal("quantity"), rs.getBigDecimal("subtotal"));
                orders.add(ord);
            }

        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred when trying to retrieve from the database");
        }

        return orders;

    }

    public List<OrderDetails> sortOrdersByDate(String userid){
        List<OrderDetails> orders = new ArrayList<>();

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM orderdetails WHERE user_id = ? ORDER BY oddate");
            ps.setString(1, userid);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                OrderDetails ord = new OrderDetails(rs.getString("id"), rs.getString("user_id"), rs.getDate("oddate"), rs.getString("equipment_id"), rs.getString("eqname"), rs.getBigDecimal("quantity"), rs.getBigDecimal("subtotal"));
                orders.add(ord);
            }

        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred when trying to retrieve from the database");
        }

        return orders;

    }
}
