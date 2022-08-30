package com.revature.yolp.daos;

import com.revature.yolp.dtos.requests.UserRoleRequest;
import com.revature.yolp.models.User;
import com.revature.yolp.models.UserRole;
import com.revature.yolp.utils.custom_exceptions.InvalidSQLException;
import com.revature.yolp.utils.database.ConnectionFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UserRoleDAO implements CrudDAO<UserRole> {
    @Override
    public void save(UserRole obj) throws IOException {
        System.out.println("===========================" + obj + "================================================");
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("insert into ers_user_roles (role_id, role) values (?, ?);");
            ps.setString(1, obj.getRole_id());
            ps.setString(2, obj.getRole());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred at UserRoleDAO.save()");
        }
    }

    @Override
    public void update(UserRole obj) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public UserRole getById(String id) {
        return null;
    }

    @Override
    public List<UserRole> getAll() {
        return null;
    }
}
