package com.revature.yolp.daos;

import com.revature.yolp.models.ReimburstmentType;
import com.revature.yolp.models.UserRole;
import com.revature.yolp.utils.custom_exceptions.InvalidSQLException;
import com.revature.yolp.utils.database.ConnectionFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ReimburstmentTypeDAO implements CrudDAO<ReimburstmentType> {

    public ReimburstmentTypeDAO() {
    }

    @Override
    public void save(ReimburstmentType obj) throws IOException {

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("insert into ers_reimbursement_types (type_id, type) values (?, ?);");
            ps.setString(1, obj.getType_id());
            ps.setString(2, obj.getType());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred at ReimburstmentTypeDAO.save()");
        }
    }


    @Override
    public void update(ReimburstmentType obj) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public ReimburstmentType getById(String id) {
        return null;
    }

    @Override
    public List<ReimburstmentType> getAll() {
        return null;
    }
}
