package com.svalero.AmazonAA.service;

import com.svalero.AmazonAA.domain.Stock;
import com.svalero.AmazonAA.domain.dto.StockDTO;
import com.svalero.AmazonAA.exception.InventoryNotFoundException;
import com.svalero.AmazonAA.exception.ProductNotFoundException;
import com.svalero.AmazonAA.exception.StockNotFoundException;

import java.util.List;

public interface StockService {
    List<Stock> findAll();
    Stock findById(long id) throws  StockNotFoundException;
    List<Stock> findByProduct(long id);
    List<Stock> findByInventory(long id);
    Stock addStock(StockDTO stockDTO) throws ProductNotFoundException, InventoryNotFoundException;
    Stock modifyStock(long id, StockDTO stockDTO) throws StockNotFoundException, InventoryNotFoundException, ProductNotFoundException;
    void deleteStock(long id) throws  StockNotFoundException;
}
