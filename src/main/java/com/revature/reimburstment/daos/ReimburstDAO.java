package com.revature.reimburstment.daos;

import com.revature.reimburstment.dtos.requests.ReimburstmentFullRequest;
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
import java.util.ArrayList;
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
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("update ers_reimbursements set payment_id = ?, status_id = ? where reimb_id = ? ;");

            System.out.println("payment_id: " + obj.getPayment_id());
            System.out.println("status_id: " + obj.getStatus_id());
            System.out.println("reimb_id: " + obj.getReimb_id());

            ps.setString(1,obj.getPayment_id());
            ps.setString(2, obj.getStatus_id());
            ps.setString(3, obj.getReimb_id());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred when tyring to ReimburstDAO.save() to the database.");
        }
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Reimburstment getById(String id) {
        return null;
    }

    public List<ReimburstmentFullRequest> getAllReimburstForRequest() {
        List<ReimburstmentFullRequest> reimList = new ArrayList<>();

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("select r.reimb_id, r.amount, r.submitted, r.description, r.payment_id, u.given_name as author_first_name, u.surname as author_last_name, u1.given_name as resolver_first_name, u1.surname as resolver_last_name, t.type as reimburst_type , s.status as reimburst_status  from ers_reimbursements r\n" +
                            "\tinner join ers_reimbursement_statuses s on r.status_id = s.status_id \n" +
                            "\tinner join ers_reimbursement_types t on r.type_id  = t.type_id\n" +
                            "\tinner join ers_users u on r.author_id = u.user_id\n" +
                            "\tinner join ers_users u1 on r.resolver_id = u1.user_id;");
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                ReimburstmentFullRequest r = new ReimburstmentFullRequest();
                r.setReimb_id(rs.getString("reimb_id"));
                r.setAmount(rs.getBigDecimal("amount"));
                r.setSubmitted(rs.getTimestamp("submitted"));
                r.setDescription(rs.getString("description"));
                r.setPayment_id(rs.getString("payment_id"));
                r.setAuthor_first_name(rs.getString("author_first_name"));
                r.setAuthor_last_name(rs.getString("author_last_name"));
                r.setResolver_first_name(rs.getString("resolver_first_name"));
                r.setResolver_last_name(rs.getString("resolver_last_name"));
                r.setReimburst_type(rs.getString("reimburst_type"));
                r.setReimburst_status(rs.getString("reimburst_status"));
                System.out.println(r);
                reimList.add(r);
            }
            return reimList;
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred when tyring to save to the database.");
        }
    }

    @Override
    public List<Reimburstment> getAll() {
        List<Reimburstment> reimList = new ArrayList<>();

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("select r.reimb_id, r.amount, r.submitted, r.description, r.payment_id, u.given_name as author_first_name, u.surname as author_last_name, u1.given_name as resolver_first_name, u1.surname as resolver_last_name, t.type as reimburst_type , s.status as reimburst_status  from ers_reimbursements r\n" +
                            "\tinner join ers_reimbursement_statuses s on r.status_id = s.status_id \n" +
                            "\tinner join ers_reimbursement_types t on r.type_id  = t.type_id\n" +
                            "\tinner join ers_users u on r.author_id = u.user_id\n" +
                            "\tinner join ers_users u1 on r.resolver_id = u1.user_id;");
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Reimburstment r = new Reimburstment();
                r.setReimb_id(rs.getString("reimb_id"));
                r.setAmount(rs.getBigDecimal("amount"));
                r.setSubmitted(rs.getTimestamp("submitted"));
                r.setResolved(rs.getTimestamp("resolved"));
                r.setDescription(rs.getString("description"));
                r.setReceipt(rs.getBinaryStream("receipt"));
                r.setPayment_id(rs.getString("payment_id"));
                r.setAuthor_id(rs.getString("author_id"));
                r.setResolver_id(rs.getString("resolver_id"));
                r.setStatus_id(rs.getString("status_id"));
                r.setType_id(rs.getString("type_id"));
                reimList.add(r);
            }
            return reimList;
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred when tyring to save to the database.");
        }
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
