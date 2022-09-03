package com.revature.reimburstment.daos;

import com.revature.reimburstment.models.ReimburstmentStatus;
import com.revature.reimburstment.utils.custom_exceptions.InvalidSQLException;
import com.revature.reimburstment.utils.database.ConnectionFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReimburstStatusDAO implements CrudDAO<ReimburstmentStatus> {
    @Override
    public void save(ReimburstmentStatus obj) throws IOException {

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("insert into ers_reimbursement_statuses (status_id, status) values (?, ?)");
            ps.setString(1, obj.getStastus_id());
            ps.setString(2, obj.getStatus());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred at UserReimburstStatusDAO.save()");
        }
    }



    @Override
    public void update(ReimburstmentStatus obj) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("update ers_reimbursement_statuses set status = ? where status_id = ?");
            ps.setString(1, obj.getStatus());
            ps.setString(2, obj.getStastus_id());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred at UserRoleDAO.update()");
        }
    }

    @Override
    public void delete(String id) {

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("delete from ers_reimbursement_statuses where status_id = ?");
            ps.setString(1, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred at UserRoleDAO.delete()");
        }
    }

    @Override
    public ReimburstmentStatus getById(String id) {
        ReimburstmentStatus userRole = new ReimburstmentStatus();
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("select * from ers_user_roles where role_id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                userRole.setStastus_id(rs.getString("status_id"));
                userRole.setStatus(rs.getString("status"));
            }

        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred at UserRoleDAO.delete()");
        }
        return userRole;
    }

    @Override
    public List<ReimburstmentStatus> getAll() {
        List<ReimburstmentStatus> statusList = new ArrayList<>();

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("select * from ers_reimbursement_statuses");
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                ReimburstmentStatus reimburstStatus =
                        new ReimburstmentStatus(rs.getString("status_id"), rs.getString("status"));
                statusList.add(reimburstStatus);
            }
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred when trying to getAll() status from database.");
        }
        System.out.println(statusList);
        return statusList;
    }
}
