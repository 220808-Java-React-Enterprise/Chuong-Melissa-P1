package com.revature.strong.daos;

import com.revature.strong.models.Equipment;
import com.revature.strong.utils.custom_exceptions.InvalidSQLException;
import com.revature.strong.utils.database.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EquipmentDOA implements CrudDAO<Equipment> {
    @Override
    public void save(Equipment obj) {

    }

    @Override
    public void update(Equipment obj) {

    }

    @Override
    public void delete(Equipment obj) {

    }

    @Override
    public Equipment getById(String id) {
        return null;
    }

    @Override
    public List<Equipment> getAll() {
        List<Equipment> equipment = new ArrayList<>();

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM equipment");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Equipment equip = new Equipment(rs.getString("id"), rs.getString("eqname"), rs.getString("description"), rs.getBigDecimal("price"));
                equipment.add(equip);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new InvalidSQLException("An error occurred when trying to retrieve from the database");


        }

        return equipment;
    }

    public Equipment getEquipmentById(String id){
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM equipment WHERE id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return new Equipment(rs.getString("id"), rs.getString("eqname"), rs.getString("description"), rs.getBigDecimal("price"));

        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred when trying to save to the database");
        }

        return null;
    }
}

