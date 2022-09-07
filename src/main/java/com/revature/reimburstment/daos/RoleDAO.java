package com.revature.reimburstment.daos;

import com.revature.reimburstment.models.User;
import com.revature.reimburstment.models.UserRole;
import com.revature.reimburstment.utils.custom_exceptions.InvalidSQLException;
import com.revature.reimburstment.utils.database.ConnectionFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDAO implements CrudDAO<UserRole> {
    @Override
    public void save(UserRole obj) throws IOException {

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("insert into ers_user_roles (role_id, role) values (?, ?)");
            ps.setString(1, obj.getRole_id());
            ps.setString(2, obj.getRole());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred at UserRoleDAO.save() " + e.getMessage());
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
            throw new InvalidSQLException("An error occurred at UserRoleDAO.update() " + e.getMessage());
        }
    }

    @Override
    public void delete(String id) {

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("delete from ers_user_roles where role_id = ?");
            ps.setString(1, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred at UserRoleDAO.delete() " + e.getMessage());
        }
    }

    @Override
    public UserRole getById(String id) {
        UserRole userRole =new UserRole();
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("select * from ers_user_roles where role_id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                userRole.setRole_id(rs.getString("role_id"));
                userRole.setRole(rs.getString("role"));
            }

        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred at UserRoleDAO.getById() " +  e.getMessage());
        }
        return userRole;
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
            throw new InvalidSQLException("An error occurred when trying to UserRoleDAO.getAll() roles from database. " + e.getMessage());
        }
        System.out.println(roleList);
        return roleList;
    }

    public UserRole getByRole(String role) {
        UserRole userRole = null;

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("select * from ers_user_roles where role = ?");
            ps.setString(1, role);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                userRole = new UserRole(rs.getString("role_id"), rs.getString("role"));
            }
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred when trying to UserRoleDAO.getByRole() roles from database. " + e.getMessage());
        }
        return userRole;
    }
}
