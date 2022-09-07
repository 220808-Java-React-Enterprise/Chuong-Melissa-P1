package com.revature.reimburstment.daos;

import com.revature.reimburstment.models.User;
import com.revature.reimburstment.utils.custom_exceptions.InvalidSQLException;
import com.revature.reimburstment.utils.database.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// all sql script that save on local disk
//C:\Users\JOHN\AppData\Roaming\DBeaverData\workspace6\General\Scripts


// mvn tomcat7:deploy
public class UserDAO implements CrudDAO<User> {

    @Override
    public void save(User obj) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement(
"INSERT INTO ers_users (user_id, username, email, password, given_name, surname, is_active, role_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, obj.getUser_id());
            ps.setString(2, obj.getUsername());
            ps.setString(3, obj.getEmail());
            ps.setString(4, obj.getPassword());
            ps.setString(5, obj.getGiven_name());
            ps.setString(6, obj.getSurname());
            ps.setBoolean(7, obj.is_active());
            ps.setString(8, obj.getRole_id());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred when tyring to UserDAO.save() to the database." + e.getMessage());
        }
    }

    @Override
    public void update(User obj) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement(
                            "update ers_users set is_active = ?, role_id = ? where user_id = ?;");
            ps.setBoolean(1, obj.is_active());
            ps.setString(2, obj.getRole_id());
            ps.setString(3, obj.getUser_id());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred when tyring to save to the database.");
        }
    }

    @Override
    public void delete(String id) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("update ers_users set is_active = ? where user_id = ? ;");
            ps.setBoolean(1, false);
            ps.setString(2, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred when tyring to save to the database.");
        }
    }

    @Override
    public User getById(String id) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(rs.getString("id"), rs.getString("username"), rs.getString("password"), rs.getString("role_id"));
            }

        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred when tyring to save to the database.");
        }

        return null;
    }

    @Override
    public List<User> getAll() {
        List<User> userList = new ArrayList<>();

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("select * from ers_users");
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                User user = new User();
                user.setUser_id(rs.getString("user_id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setGiven_name(rs.getString("given_name"));
                user.setSurname(rs.getString("surname"));
                user.setIs_active(rs.getBoolean("is_active"));
                user.setRole_id(rs.getString("role_id"));
                userList.add(user);
            }
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred when tyring to save to the database.");
        }

        return userList;
    }

    public String getUsername(String username) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT (username) FROM ers_users WHERE username = ?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return rs.getString("username");

        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred when tyring to save to the database.");
        }

        return null;
    }

    public User getUserByUsernameAndPassword(String username, String password) {
        User user = null;
//        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
//            PreparedStatement ps = con.prepareStatement("select * from ers_users where username = ? and password = ?");
//            ps.setString(1, username);
//            ps.setString(2, password);
//            ResultSet rs = ps.executeQuery();
//
//            if (rs.next()) {
//                String user_id = rs.getString("user_id");
//                String usernameTemp = rs.getString("username");
//                String role_id = rs.getString("role_id");
//                boolean is_active = rs.getBoolean("is_active");
//                user = new User();
//                user.setUser_id(user_id);
//                user.setUsername(usernameTemp);
//                user.setRole_id(role_id);
//                user.setIs_active(is_active);
//                return user;
//            }
//        } catch (SQLException e) {
//            throw
//                    new InvalidSQLException(
//                            "An error occurred when tyring to getUserByUsernameAndPassword() to the database." +
//                            "username:" + username +
//                            "  password:" + password + " : " + e.getMessage());
//        }

        return null;
    }

    public User getUserByRoleId(String role_id) {
        System.out.println("Role id: " + role_id);
        User user = null;
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("select * from ers_users where role_id = ?");
            ps.setString(1, role_id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setUser_id(rs.getString("user_id"));
            }
        } catch (SQLException e) {
            throw
                    new InvalidSQLException(
                            "An error occurred when tyring to getByRoleId()");
        }

        return user;
    }
}
