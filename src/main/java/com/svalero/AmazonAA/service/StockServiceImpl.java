package com.svalero.AmazonAA.service;

import com.svalero.AmazonAA.domain.Inventory;
import com.svalero.AmazonAA.domain.Product;
import com.svalero.AmazonAA.domain.Stock;
import com.svalero.AmazonAA.domain.dto.StockDTO;
import com.svalero.AmazonAA.exception.InventoryNotFoundException;
import com.svalero.AmazonAA.exception.ProductNotFoundException;
import com.svalero.AmazonAA.exception.StockNotFoundException;
import com.svalero.AmazonAA.repository.InventoryRepository;
import com.svalero.AmazonAA.repository.ProductRepository;
import com.svalero.AmazonAA.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockServiceImpl implements StockService{

    @Autowired
    StockRepository stockRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    InventoryRepository inventoryRepository;

    private final Logger logger = LoggerFactory.getLogger(StockServiceImpl.class);

    @Override
    public List<Stock> findAll() {
        return stockRepository.findAll();
    }

    @Override
    public Stock findById(long id) throws StockNotFoundException {
        logger.info("ID Stock: " + id);
        return stockRepository.findById(id).orElseThrow(StockNotFoundException::new);
    }

    @Override
    public List<Stock> findByProduct(long id) {
        logger.info("ID Product Stock: " + id);
        return stockRepository.findByProductStock_Id(id);
    }

    @Override
    public List<Stock> findByInventory(long id) {
        logger.info("ID Inventory Stock: " + id);
        return stockRepository.findByInventoryStock_Id(id);
    }

    @Override
    public Stock addStock(StockDTO stockDTO) throws InventoryNotFoundException, ProductNotFoundException {
        logger.info("Added Stock: " + stockDTO);
        Stock stock = new Stock();
        Inventory inventory = inventoryRepository.findById(stockDTO.getInventoryStock()).orElseThrow(InventoryNotFoundException::new);
        Product product = productRepository.findById(stockDTO.getProductStock()).orElseThrow(ProductNotFoundException::new);

        stock.setInventoryStock(inventory);
        stock.setProductStock(product);
        stock.setQuantity(stockDTO.getQuantity());

        return stockRepository.save(stock);
    }

    @Override
    public Stock modifyStock(long id, StockDTO stockDTO) throws StockNotFoundException, InventoryNotFoundException, ProductNotFoundException {
        Stock existingStock = stockRepository.findById(id).orElseThrow(StockNotFoundException::new);
        logger.info("Existing Stock: " + existingStock);
        logger.info("Modified Stock: " + stockDTO);
        Inventory inventory = inventoryRepository.findById(stockDTO.getInventoryStock()).orElseThrow(InventoryNotFoundException::new);
        Product product = productRepository.findById(stockDTO.getProductStock()).orElseThrow(ProductNotFoundException::new);

        existingStock.setInventoryStock(inventory);
        existingStock.setProductStock(product);
        existingStock.setQuantity(stockDTO.getQuantity());

        return stockRepository.save(existingStock);
    }

    @Override
    public void deleteStock(long id) throws StockNotFoundException {
        Stock existingStock = stockRepository.findById(id).orElseThrow(StockNotFoundException::new);
        logger.info("Deleted Stock: " + existingStock);

        stockRepository.delete(existingStock);
    }
}
