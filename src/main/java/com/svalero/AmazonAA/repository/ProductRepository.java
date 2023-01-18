package com.svalero.AmazonAA.repository;

import com.svalero.AmazonAA.domain.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findAll();
    List<Product> findByNameContaining(String name);
    List<Product> findByCategory(String category);
    List<Product> findByDescriptionContainingIgnoreCase(String description);
    @Query(value = "SELECT \"product\".* FROM \"product\"\n" +
            "JOIN \"stock\" on \"stock\".\"product_id\"  = \"product\".\"id\"\n" +
            "WHERE \"stock\".\"quantity\" = 0\n" +
            "GROUP BY \"product\".\"id\"", nativeQuery = true)
    List<Product> findSoldOut();

}
