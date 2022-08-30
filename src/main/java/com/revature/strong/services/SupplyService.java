package com.revature.strong.services;

import com.revature.strong.daos.CrudDAO;
import com.revature.strong.daos.SupplyDAO;
import com.revature.strong.models.Supply;
import com.revature.strong.utils.custom_exceptions.InvalidUserException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class SupplyService implements CrudDAO<Supply> {

    private final SupplyDAO supplyDAO;

    public SupplyService(SupplyDAO supplyDAO){
        this.supplyDAO = supplyDAO;
    }

    public void updateByEqname(String eqname, BigDecimal quantity, Boolean replenish){
        supplyDAO.updateByEqname(eqname, quantity, replenish);
    }

    public BigDecimal getQuantityByEqname(String eqname){
        BigDecimal quantity = supplyDAO.getQuantity(eqname);
        return quantity;
    }

    @Override
    public void save(Supply obj) throws IOException {

    }

    @Override
    public void update(Supply obj) {

    }

    @Override
    public void delete(Supply obj) {

    }

    @Override
    public Supply getById(String id) {
        Supply supply = supplyDAO.getById(id);
        if (supply == null) throw new InvalidUserException("Sorry " + id + " is not a valid id");

        return supply;
    }

    @Override
    public List<Supply> getAll() {
        return supplyDAO.getAll();
    }
}
