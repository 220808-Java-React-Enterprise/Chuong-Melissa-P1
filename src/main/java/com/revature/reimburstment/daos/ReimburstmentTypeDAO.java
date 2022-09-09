package com.revature.reimburstment.daos;

import com.revature.reimburstment.models.ReimburstmentType;
import com.revature.reimburstment.utils.custom_exceptions.InvalidSQLException;
import com.revature.reimburstment.utils.custom_exceptions.InvalidTypeException;
import com.revature.reimburstment.utils.database.ConnectionFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReimburstmentTypeDAO implements CrudDAO<ReimburstmentType> {

    public ReimburstmentTypeDAO() {
    }

    @Override
    public int save(ReimburstmentType obj) throws IOException {

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("insert into ers_reimburstments_types (type_id, type) values (?, ?);");
            ps.setString(1, obj.getType_id());
            ps.setString(2, obj.getType());

            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred at ReimburstmentTypeDAO.save(). " + e.getMessage());
        }
    }


    @Override
    public int update(ReimburstmentType obj) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("update ers_reimburstments_types set type = ? where type_id = ? ");
            ps.setString(1, obj.getType());
            ps.setString(2, obj.getType_id());

            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred at ReimburstmentTypeDAO.update(). " + e.getMessage());
        }
    }

    @Override
    public void delete(String id) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("delete from ers_reimburstments_types where type_id = ?");
            ps.setString(1, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred at ReimburstmentTypeDAO.delete(). " + e.getMessage());
        }
    }

    @Override
    public ReimburstmentType getById(String id) {
        ReimburstmentType reimburstmentType = new ReimburstmentType();
        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = conn.prepareStatement("select * from ers_reimburstments_types where type_id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                reimburstmentType.setType_id(rs.getString("type_id"));
                reimburstmentType.setType(rs.getString("type"));
            }
            return reimburstmentType;
        }catch(SQLException e) {
            throw new InvalidTypeException("Invalid Reimburstment Type ID");
        }
    }

    public ReimburstmentType getByType(String type) {
        ReimburstmentType reimburstmentType = new ReimburstmentType();
        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = conn.prepareStatement("select * from ers_reimburstments_types where type = ?");
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                reimburstmentType.setType_id(rs.getString("type_id"));
                reimburstmentType.setType(rs.getString("type"));
            }
            return reimburstmentType;
        }catch(SQLException e) {
            throw new InvalidTypeException("Invalid Reimburstment Type ID");
        }
    }

    @Override
    public List<ReimburstmentType> getAll() {
        List<ReimburstmentType> typesList = new ArrayList<>();

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("select * from ers_reimburstments_types");
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                ReimburstmentType t =
                        new ReimburstmentType(rs.getString("type_id"), rs.getString("type")
                );
                typesList.add(t);
            }
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred when tyring to ReimburstmentTypeDAO.getAll() to the database. " + e.getMessage());
        }

        return typesList;
    }
}
