package com.svalero.AmazonAA.repository;

import com.svalero.AmazonAA.domain.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findAll();
    Product findById();
    List<Product> findByName();
}