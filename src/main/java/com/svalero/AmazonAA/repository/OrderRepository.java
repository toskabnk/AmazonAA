package com.svalero.AmazonAA.repository;

import com.svalero.AmazonAA.domain.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findAll();
    Order findById();
    List<Order> findByCustomer();

}
