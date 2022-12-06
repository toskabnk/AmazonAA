package com.svalero.AmazonAA.repository;

import com.svalero.AmazonAA.domain.Stock;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StockRepository extends CrudRepository<Stock, Long> {
    List<Stock> findAll();
}
