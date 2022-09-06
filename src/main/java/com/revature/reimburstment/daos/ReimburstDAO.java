package com.revature.reimburstment.daos;

import com.revature.reimburstment.dtos.requests.ReimburstmentFullRequest;
import com.revature.reimburstment.models.Reimburstment;
import com.revature.reimburstment.models.ReimburstmentStatus;
import com.revature.reimburstment.models.User;
import com.revature.reimburstment.models.UserRole;
import com.revature.reimburstment.utils.custom_exceptions.InvalidSQLException;
import com.revature.reimburstment.utils.database.ConnectionFactory;

import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReimburstDAO implements CrudDAO<Reimburstment> {
    @Override
    public void save(Reimburstment obj) throws IOException {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement(
"INSERT INTO ers_reimburstments (reimb_id, amount, submitted, resolved, description, receipt, payment_id, author_id, resolver_id, status_id, type_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
            ps.setString(1, obj.getReimb_id());
            ps.setBigDecimal(2, obj.getAmount());
            ps.setTimestamp(3, obj.getSubmitted());
            ps.setTimestamp(4, obj.getResolved());
            ps.setString(5, obj.getDescription());

            if(obj.getReceipt() == null) {
                ps.setNull(9, Types.ARRAY);
            }else {
                ps.setBytes(6, obj.getReceipt());
            }

            if(obj.getPayment_id() == null) {
                ps.setNull(7, Types.VARCHAR);
            }else {
                ps.setString(7, obj.getPayment_id());
            }
            ps.setString(8, obj.getAuthor_id());

            if(obj.getResolver_id() == null) {
                ps.setNull(9, Types.VARCHAR);
            }else {
                ps.setString(9, obj.getResolver_id());
            }
            ps.setString(10, obj.getStatus_id());
            ps.setString(11, obj.getType_id());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred when tyring to ReimburstDAO.save() to the database." + e.getMessage());
        }
    }

    @Override
    public void update(Reimburstment obj) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("update ers_reimburstments set payment_id = ?, status_id = ?, resolved = ?, resolver_id = ? where reimb_id = ? ;");

            ps.setString(1,obj.getPayment_id());
            ps.setString(2, obj.getStatus_id());
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(4, obj.getResolver_id());
            ps.setString(5, obj.getReimb_id());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred when tyring to ReimburstDAO.update() to the database. " + e.getMessage());
        }
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Reimburstment getById(String id) {
        return null;
    }

    public List<ReimburstmentFullRequest> getAllReimburstForRequest(String searchType) {
        List<ReimburstmentFullRequest> reimList = new ArrayList<>();

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            String sql = "select reim.reimb_id, reim.amount, reim.submitted, reim.resolved, reim.description, reim.payment_id,\n" +
                    "\t\tusers1.given_name as author_first_name, users1.surname as author_last_name,\n" +
                    "\t\tusers2.given_name as resolver_first_name, users2.surname as resolver_last_name,\n" +
                    "\t\trt.type as reimburst_type, rs.status as reimburst_status  \n" +
                    "\tfrom ers_reimburstments reim\n" +
                    "\t\tinner join ers_reimburstments_statuses rs\n" +
                    "\t\t\ton  reim.status_id = rs.status_id \n" +
                    "\t\tinner join ers_reimburstments_types rt \n" +
                    "\t\t\ton reim.type_id = rt.type_id \n" +
                    "\t\tinner join ers_users users1 on reim.author_id = users1.user_id\n" +
                    "\t\tinner join ers_users users2 on reim.resolver_id  = users2.user_id\n" +
                    "\t\n" +
                    "\twhere rt.type = ?;";
            PreparedStatement ps =
                    con.prepareStatement(sql);
            ps.setString(1, searchType);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                ReimburstmentFullRequest r = new ReimburstmentFullRequest();
                r.setReimb_id(rs.getString("reimb_id"));
                r.setAmount(rs.getBigDecimal("amount"));
                r.setSubmitted(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(rs.getTimestamp("submitted")));
                r.setResolved(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(rs.getTimestamp("resolved")));
                r.setDescription(rs.getString("description"));
                r.setPayment_id(rs.getString("payment_id"));
                r.setAuthor_first_name(rs.getString("author_first_name"));
                r.setAuthor_last_name(rs.getString("author_last_name"));

                if(rs.getString("resolver_first_name") == null) {
                    r.setResolver_first_name("Not yet assigned");
                }else {
                    r.setResolver_first_name(rs.getString("resolver_first_name"));
                }

                if(rs.getString("resolver_last_name") == null) {
                    r.setResolver_last_name("Not yet assigned");
                }else {
                    r.setResolver_last_name(rs.getString("resolver_last_name"));
                }



                r.setReimburst_type(rs.getString("reimburst_type"));
                r.setReimburst_status(rs.getString("reimburst_status"));

                reimList.add(r);
            }
            return reimList;
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred when tyring to ReimburstDAO.getAllReimburstForRequest(String searchType, String searchStatus) to the database. " + e.getMessage());
        }
    }

    public List<ReimburstmentFullRequest> getAllReimburstForRequest() {
        List<ReimburstmentFullRequest> reimList = new ArrayList<>();

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            String sql = "select \n" +
                    "\tr.reimb_id, r.amount, r.submitted, r.resolved, r.description, r.payment_id, \n" +
                    "\tu.given_name as author_first_name, u.surname as author_last_name, \n" +
                    "\tu1.given_name as resolver_first_name, u1.surname as resolver_last_name, \n" +
                    "\tt.type as reimburst_type , s.status as reimburst_status  \n" +
                    "from ers_reimburstments r\n" +
                    "\tinner join ers_reimburstments_statuses s on r.status_id = s.status_id \n" +
                    "\tinner join ers_reimburstments_types t on r.type_id  = t.type_id\n" +
                    "\tinner join ers_users u on r.author_id = u.user_id\n" +
                    "\tinner join ers_users u1 on r.resolver_id = u1.user_id;";
            PreparedStatement ps =
                    con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                ReimburstmentFullRequest r = new ReimburstmentFullRequest();
                r.setReimb_id(rs.getString("reimb_id"));
                r.setAmount(rs.getBigDecimal("amount"));
                r.setSubmitted(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(rs.getTimestamp("submitted")));

                if(rs.getTimestamp("resolved") == null) {
                    r.setResolved("Process soon...");
                } else {
                    r.setResolved(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(rs.getTimestamp("resolved")));
                }

                r.setDescription(rs.getString("description"));
                r.setPayment_id(rs.getString("payment_id"));
                r.setAuthor_first_name(rs.getString("author_first_name"));
                r.setAuthor_last_name(rs.getString("author_last_name"));

                if(rs.getString("resolver_first_name") == null) {
                    r.setResolver_first_name("Not yet assigned");
                }else {
                    r.setResolver_first_name(rs.getString("resolver_first_name"));
                }

                if(rs.getString("resolver_last_name") == null) {
                    r.setResolver_last_name("Not yet assigned");
                }else {
                    r.setResolver_last_name(rs.getString("resolver_last_name"));
                }



                r.setReimburst_type(rs.getString("reimburst_type"));
                r.setReimburst_status(rs.getString("reimburst_status"));

                reimList.add(r);
            }
            return reimList;
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred when tyring to ReimburstDAO.getAllReimburstForRequest() to the database. " + e.getMessage());
        }
    }

    @Override
    public List<Reimburstment> getAll() {
        List<Reimburstment> reimList = new ArrayList<>();

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("select r.reimb_id, r.amount, r.submitted, r.description, r.payment_id, u.given_name as author_first_name, u.surname as author_last_name, u1.given_name as resolver_first_name, u1.surname as resolver_last_name, t.type as reimburst_type , s.status as reimburst_status  from ers_reimburstments r\n" +
                            "\tinner join ers_reimburstments s on r.status_id = s.status_id \n" +
                            "\tinner join ers_reimburstments_types t on r.type_id  = t.type_id\n" +
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
                r.setReceipt(rs.getBytes("receipt"));
                r.setPayment_id(rs.getString("payment_id"));
                r.setAuthor_id(rs.getString("author_id"));
                r.setResolver_id(rs.getString("resolver_id"));
                r.setStatus_id(rs.getString("status_id"));
                r.setType_id(rs.getString("type_id"));
                reimList.add(r);
            }
            return reimList;
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred when tyring to ReimburstDAO.getAll() to the database. " + e.getMessage());
        }
    }

    public ReimburstmentStatus getByStatus(String status) {
        ReimburstmentStatus reimburstmentStatus = null;
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("select * from ers_reimburstments_statuses where status = ?");
            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                 reimburstmentStatus = new ReimburstmentStatus(
                         rs.getString("status_id"), rs.getString("status"));
            }
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred when tyring to ReimburstDAO,getByStatus() to the database. " + e.getMessage());
        }
        return reimburstmentStatus;
    }

    public void initializeResolver(String userId, ReimburstmentStatus status) {

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("update  ers_reimburstments set resolver_id = ? where status_id = ?;");
            ps.setString(1, userId);
            ps.setString(2, status.getStastus_id());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred when tyring to ReimburstDAO.initializeResolverId() to the database. " + e.getMessage());
        }
    }
}
