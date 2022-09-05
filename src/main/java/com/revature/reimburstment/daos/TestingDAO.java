package com.revature.reimburstment.daos;

import com.revature.reimburstment.models.Testing;
import com.revature.reimburstment.models.User;
import com.revature.reimburstment.utils.custom_exceptions.InvalidSQLException;
import com.revature.reimburstment.utils.database.ConnectionFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestingDAO implements CrudDAO <Testing>{
    @Override
    public void save(Testing obj) throws IOException {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement(
 "insert into ers_reimbursements (reimb_id, amount, description, receipt, payment_id, author_id, resolver_id, status_id, type_id) values (?, ?, ?, ?, ?, ?, ?, ?, ?);");
            ps.setString(1, UUID.randomUUID().toString());
            ps.setBigDecimal(2, obj.getAmount());
            ps.setString(3, obj.getDescription());
            ps.setBinaryStream(4, obj.getInputStream());
            ps.setString(5, "123");
            ps.setString(6, "123");
            ps.setString(7, "123");
            ps.setString(8, "123");
            ps.setString(9, obj.getType());

            System.out.println(UUID.randomUUID().toString());
            System.out.println("amount: " + obj.getAmount());
            System.out.println("description: " + obj.getDescription());
            System.out.println("type: " + obj.getType());
            if(obj.getInputStream() != null) {
                System.out.println("inputStream is not null");
            }
           ps.executeUpdate();
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred when tyring to save to the database.");
        }
    }

    @Override
    public void update(Testing obj) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Testing getById(String id) {
        return null;
    }

    @Override
    public List<Testing> getAll() {
        List<Testing> list = new ArrayList<>();

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("select amount, description from ers_reimbursements where reimb_id = ?");
            ps.setString(1, "171758db-fb07-424e-9655-97873e6af21d");
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Testing t = new Testing();
                t.setAmount(rs.getBigDecimal("amount"));
                t.setDescription(rs.getString("description"));
                //t.setInputStream(rs.getBinaryStream("receipt"));


                list.add(t);
            }

            return list;
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred when tyring to save to the database.");
        }


    }
}
