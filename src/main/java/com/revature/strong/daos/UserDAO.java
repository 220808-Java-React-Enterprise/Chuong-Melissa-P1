package com.revature.strong.daos;

import com.revature.strong.models.User;
import com.revature.strong.utils.custom_exceptions.InvalidSQLException;
import com.revature.strong.utils.database.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements CrudDAO<User>{

    @Override
    public void save(User obj) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO users (id, username, userpassword, coach, coach_id) VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, obj.getId());
            ps.setString(2, obj.getUsername());
            ps.setString(3, obj.getUserPassword());
            ps.setBoolean(4, obj.getCoach());
            ps.setString(5, obj.getCoach_id());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new InvalidSQLException("An error occurred when trying to save to the database");
        }
    }

    @Override
    public void update(User obj) {

    }

    @Override
    public void delete(User obj) {

    }

    @Override
    public User getById(String id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users ");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User(rs.getString("username"), rs.getString("userpassword"), rs.getString("id"), rs.getBoolean("coach"), rs.getString("coach_id"));
                users.add(user);
            }

        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred when trying to retrieve from the database");
        }
        return users;
    }

    public String getUsername(String username) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT (username) FROM users WHERE username = ?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return rs.getString("username");

        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred when trying to save to the database");
        }

        return null;
    }

    public User getUserByUsernameAndPassword(String username, String password){
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE username = ? AND userpassword = ?");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return new User(rs.getString("username"), rs.getString("userpassword"), rs.getString("id"), rs.getBoolean("coach"), rs.getString("coach_id"));

        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred when trying to save to the database");
        }

        return null;
    }

    public List<String> getAllUsernames(){
        return null;
    }

    public User getUserByUsername(String username){
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE username = ?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return new User(rs.getString("username"), rs.getString("userpassword"), rs.getString("id"), rs.getBoolean("coach"));

        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred when trying to save to the database");
        }

        return null;
    }
}
