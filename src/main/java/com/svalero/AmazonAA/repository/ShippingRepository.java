package com.svalero.AmazonAA.repository;

import com.svalero.AmazonAA.domain.Order;
import com.svalero.AmazonAA.domain.Shipping;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShippingRepository extends CrudRepository<Shipping, Long> {
    List<Shipping> findAll();
    Shipping findByOrder(Order order);
    List<Shipping> findByCarrierContainingIgnoreCase(String carrrier);

}
