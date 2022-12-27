package com.svalero.AmazonAA.repository;

import com.svalero.AmazonAA.domain.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findAll();
    List<Order> findByCustomer_Id(long id);
    List<Order> findByProduct_Id(long id);
    List<Order> findByPaidEquals(boolean paid);

}
