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
                    con.prepareStatement("insert into ers_reimburstments_statuses (status_id, status) values (?, ?)");
            ps.setString(1, obj.getStastus_id());
            ps.setString(2, obj.getStatus());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred at UserReimburstStatusDAO.save()");
        }
    }



    @Override
    public void update(ReimburstmentStatus obj) {
        System.out.println(obj);
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("update ers_reimburstments_statuses set status = ? where status_id = ?");
            ps.setString(1, obj.getStatus());
            ps.setString(2, obj.getStastus_id());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred at ReimburstStatusDAO.update() " + e.getMessage());
        }
    }

    @Override
    public void delete(String id) {

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("delete from ers_reimburstments_statuses where status_id = ?");
            ps.setString(1, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred at ReimburstStatusDAO.delete() " + e.getMessage());
        }
    }

    @Override
    public ReimburstmentStatus getById(String id) {
        ReimburstmentStatus reimStatus = new ReimburstmentStatus();
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("select * from ers_reimburstments_statuses where status_id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                reimStatus.setStastus_id(rs.getString("status_id"));
                reimStatus.setStatus(rs.getString("status"));
            }

        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred at ReimburstStatusDAO.getById() " + e.getMessage());
        }
        return reimStatus;
    }

    public ReimburstmentStatus getByStatus(String status) {
        ReimburstmentStatus reimStatus = new ReimburstmentStatus();
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("select * from ers_reimburstments_statuses where status = ?");
            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                reimStatus.setStastus_id(rs.getString("status_id"));
                reimStatus.setStatus(rs.getString("status"));
            }

        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred at ReimburstStatusDAO.getByStatus() " + e.getMessage());
        }
        return reimStatus;
    }

    @Override
    public List<ReimburstmentStatus> getAll() {
        List<ReimburstmentStatus> statusList = new ArrayList<>();

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("select * from ers_reimburstments_statuses");
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                ReimburstmentStatus reimburstStatus =
                        new ReimburstmentStatus(rs.getString("status_id"), rs.getString("status"));
                statusList.add(reimburstStatus);
            }
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred when trying to ReimburstStatusDAO() status from database.");
        }
        System.out.println(statusList);
        return statusList;
    }
}
