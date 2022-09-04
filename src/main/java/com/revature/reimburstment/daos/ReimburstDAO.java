package com.revature.reimburstment.daos;

import com.revature.reimburstment.models.Reimburstment;
import com.revature.reimburstment.models.ReimburstmentStatus;
import com.revature.reimburstment.models.User;
import com.revature.reimburstment.utils.custom_exceptions.InvalidSQLException;
import com.revature.reimburstment.utils.database.ConnectionFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class ReimburstDAO implements CrudDAO<Reimburstment> {
    @Override
    public void save(Reimburstment obj) throws IOException {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement(
"INSERT INTO ers_reimbursements (reimb_id, amount, description, payment_id, author_id, resolver_id, status_id, type_id, submitted) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);");
            ps.setString(1, obj.getReimb_id());
            ps.setBigDecimal(2, obj.getAmount());
            ps.setString(3, obj.getDescription());
            ps.setString(4, obj.getPayment_id());
            ps.setString(5, obj.getAuthor_id());
            ps.setString(6, obj.getResolver_id());
            ps.setString(7, obj.getStatus_id());
            ps.setString(8, obj.getType_id());
            ps.setTimestamp(9, obj.getSubmitted());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred when tyring to ReimburstDAO.save() to the database.");
        }
    }

    @Override
    public void update(Reimburstment obj) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Reimburstment getById(String id) {
        return null;
    }

    @Override
    public List<Reimburstment> getAll() {
        return null;
    }

    public ReimburstmentStatus getByStatus(String status) {
        ReimburstmentStatus reimburstmentStatus = null;
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("select * from ers_reimbursement_statuses where status = ?");
            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                 reimburstmentStatus = new ReimburstmentStatus(
                         rs.getString("status_id"), rs.getString("status"));
            }
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred when tyring to save to the database.");
        }
        return reimburstmentStatus;
    }
}
