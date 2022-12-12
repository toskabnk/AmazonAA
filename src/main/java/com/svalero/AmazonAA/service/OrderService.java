package com.svalero.AmazonAA.service;

import com.svalero.AmazonAA.domain.Order;
import com.svalero.AmazonAA.domain.dto.OrderDTO;
import com.svalero.AmazonAA.exception.OrderNotFoundException;
import com.svalero.AmazonAA.exception.PersonNotFoundException;
import com.svalero.AmazonAA.exception.ProductNotFoundException;

import java.util.List;

public interface OrderService {
    List<Order> findAll();
    Order findById(long id) throws OrderNotFoundException;
    List<Order> findByPersonUsername(String username) throws PersonNotFoundException;
    List<Order> findByProductId(long id) throws ProductNotFoundException;

    Order addOrder(OrderDTO orderDTO) throws PersonNotFoundException, ProductNotFoundException;
    void deleteOrder(long id) throws OrderNotFoundException;
    Order modifyOrder(long id, OrderDTO orderDTO) throws OrderNotFoundException, ProductNotFoundException;



}
