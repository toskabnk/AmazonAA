package com.svalero.AmazonAA.repository;

import com.svalero.AmazonAA.domain.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findAll();
    List<Product> findByNameContaining(String name);
    List<Product> findByCategory(String category);

}
