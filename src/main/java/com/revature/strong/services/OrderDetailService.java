package com.revature.strong.services;

import com.revature.strong.daos.OrderDetailDAO;
import com.revature.strong.models.OrderDetails;
import com.revature.strong.utils.custom_exceptions.InvalidUserException;

import java.util.List;

public class OrderDetailService {

    private final OrderDetailDAO orderdetailDAO;

    public OrderDetailService(OrderDetailDAO orderdetailDAO){
        this.orderdetailDAO = orderdetailDAO;
    }

    public void saveOrder(OrderDetails order){
        orderdetailDAO.save(order);
    }

    public List<OrderDetails> findOrdersByUserId(String userId){
        List<OrderDetails> orderdets = orderdetailDAO.getAllById(userId);
        if (orderdets.isEmpty() == true) throw new InvalidUserException("Sorry there are no orders for this user");

        return orderdets;
    }

    public List<OrderDetails> sortOrdersByPrice(String userId){
        List<OrderDetails> orderdets = orderdetailDAO.sortOrdersByPrice(userId);
        if (orderdets.isEmpty() == true) throw new InvalidUserException("Sorry there are no orders for this user");

        return orderdets;
    }

    public List<OrderDetails> sortOrdersByDate(String userId){
        List<OrderDetails> orderdets = orderdetailDAO.sortOrdersByDate(userId);
        if (orderdets.isEmpty() == true) throw new InvalidUserException("Sorry there are no orders for this user");

        return orderdets;
    }


}
