package com.svalero.AmazonAA.repository;

import com.svalero.AmazonAA.domain.Stock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface StockRepository extends CrudRepository<Stock, Long> {
    List<Stock> findAll();
    List<Stock> findByProductStock_Id(long productId);
    List<Stock> findByInventoryStock_Id(long inventoryId);
}
