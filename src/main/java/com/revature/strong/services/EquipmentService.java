package com.revature.strong.services;

import com.revature.strong.daos.EquipmentDOA;
import com.revature.strong.models.Equipment;
import com.revature.strong.utils.custom_exceptions.InvalidUserException;

import java.util.List;

public class EquipmentService {

    private final EquipmentDOA equipmentDOA;

    public EquipmentService (EquipmentDOA equipmentDOA) {
        this.equipmentDOA = equipmentDOA;
    }

    public List<Equipment> getAllEquipment() {
        return equipmentDOA.getAll();
    }

    public Equipment findEquipmentByID (String id){
        Equipment equipment = equipmentDOA.getEquipmentById(id);
        if (equipment == null) throw new InvalidUserException("Sorry " + id + " is not a valid id");

        return equipment;
    }
}
