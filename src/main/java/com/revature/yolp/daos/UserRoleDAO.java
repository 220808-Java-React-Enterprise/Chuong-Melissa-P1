package com.revature.yolp.daos;

import com.revature.yolp.dtos.requests.UserRoleRequest;
import com.revature.yolp.models.ReimburstmentType;
import com.revature.yolp.models.User;
import com.revature.yolp.models.UserRole;
import com.revature.yolp.utils.custom_exceptions.InvalidSQLException;
import com.revature.yolp.utils.database.ConnectionFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRoleDAO implements CrudDAO<UserRole> {
    @Override
    public void save(UserRole obj) throws IOException {

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("insert into ers_user_roles (role_id, role) values (?, ?)");
            ps.setString(1, obj.getRole_id());
            ps.setString(2, obj.getRole());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred at UserRoleDAO.save()");
        }
    }

    @Override
    public void update(UserRole obj) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("update ers_user_roles set role = ? where role_id = ?");
            ps.setString(1, obj.getRole());
            ps.setString(2, obj.getRole_id());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred at UserRoleDAO.update()");
        }
    }

    @Override
    public void delete(String id) {

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("delete from ers_user_roles where role = ?");
            ps.setString(1, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred at UserRoleDAO.delete()");
        }
    }

    @Override
    public UserRole getById(String id) {
        return null;
    }

    @Override
    public List<UserRole> getAll() {
        List<UserRole> roleList = new ArrayList<>();

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("select * from ers_user_roles");
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                UserRole userRole = new UserRole(rs.getString("role_id"), rs.getString("role"));
                roleList.add(userRole);
            }
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred when trying to getAll() roles from database.");
        }

        return roleList;
    }
}
