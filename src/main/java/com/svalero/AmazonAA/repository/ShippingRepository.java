package com.svalero.AmazonAA.repository;

import com.svalero.AmazonAA.domain.Order;
import com.svalero.AmazonAA.domain.Shipping;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShippingRepository extends CrudRepository<Shipping, Long> {
    List<Shipping> findAll();
    Shipping findByOrder(Order order);
    List<Shipping> findByCarrierContainingIgnoreCase(String carrrier);

    @Query(value = "SELECT \"shipping\".\"id\", \"shipping\".\"carrier\", \"shipping\".\"estimated_arrival\", \"shipping\".\"status\", \"shipping\".\"tracking\", \"shipping\".\"order_id\" FROM \"shipping\"\n" +
            "JOIN \"order\" ON \"shipping\".\"order_id\" = \"order\".\"id\"\n" +
            "JOIN \"person\" ON \"person\".\"id\" = \"order\".\"customer_id\"\n" +
            "WHERE \"shipping\".\"status\" = 'delivered' AND \"person\".\"id\" = :userID", nativeQuery = true)
    List<Shipping> findDeliveredByUser(@Param("userID") long id);

}
