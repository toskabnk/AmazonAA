package com.svalero.AmazonAA.service;

import com.svalero.AmazonAA.domain.Inventory;
import com.svalero.AmazonAA.domain.Product;
import com.svalero.AmazonAA.domain.Stock;
import com.svalero.AmazonAA.domain.dto.InventoryDTO;
import com.svalero.AmazonAA.exception.InventoryNotFoundException;
import com.svalero.AmazonAA.repository.InventoryRepository;
import com.svalero.AmazonAA.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService{

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private StockRepository stockRepository;

    private final Logger logger = LoggerFactory.getLogger(InventoryServiceImpl.class);

    @Override
    public List<Inventory> findAll() {
        return inventoryRepository.findAll();
    }

    @Override
    public Inventory findById(long id) throws InventoryNotFoundException {
        logger.info("ID Inventory: " + id);
        return inventoryRepository.findById(id).orElseThrow(InventoryNotFoundException::new);
    }

    @Override
    public List<Inventory> findByLocation(String location) {
        logger.info("Location Inventory: " + location);
        return inventoryRepository.findByLocationContainingIgnoreCase(location);
    }

    @Override
    public List<Inventory> findByAddress(String address) {
        logger.info("Address Inventory: " + address);
        return inventoryRepository.findByAddressContainingIgnoreCase(address);
    }

    @Override
    public void updateInventory(long id) throws InventoryNotFoundException{
        logger.info("ID Inventory: " + id);
        Inventory inventory = inventoryRepository.findById(id).orElseThrow(InventoryNotFoundException::new);
        List<Stock> stocks = stockRepository.findByInventoryStock_Id(id);

        float value = 0;

        logger.info("Value Inventory pre-update: " + value);
        for (Stock stock : stocks){
            Product product = stock.getProductStock();
            value += stock.getQuantity() * product.getPrice();
            logger.info("Value iteration: " + value);
        }

        logger.info("--Bucle Start-- \nValue Inventory post update: " + value);
        inventory.setTotalValue(value);
        inventoryRepository.save(inventory);
    }

    @Override
    public Inventory addInventory(InventoryDTO inventoryDTO) {
        logger.info("Inventory added: " + inventoryDTO);
        Inventory newInventory = new Inventory();

        newInventory.setAddress(inventoryDTO.getAddress());
        newInventory.setLocation(inventoryDTO.getLocation());
        newInventory.setLastUpdate(LocalDate.now());
        newInventory.setTotalValue(0);
        newInventory.setProductList(new ArrayList<>());


        return inventoryRepository.save(newInventory);
    }

    @Override
    public boolean deleteInventory(long id) throws InventoryNotFoundException {
        Inventory inventory = inventoryRepository.findById(id).orElseThrow(InventoryNotFoundException::new);
        List<Stock> stock = stockRepository.findByInventoryStock_Id(inventory.getId());
        if(!stock.isEmpty()){
            logger.error("Inventory " + inventory + " no se puede borrar ya que referencia a otras tablas en la base de datos, elimina esas referencias antes.");
            return false;
        }
        logger.info("Inventory deleted: " + inventory);
        inventoryRepository.delete(inventory);
        return true;
    }

    @Override
    public Inventory modifyInventory(long id, InventoryDTO inventoryDTO) throws InventoryNotFoundException {
        Inventory existingInventory = inventoryRepository.findById(id).orElseThrow(InventoryNotFoundException::new);
        logger.info("Inventory a modificar: " + existingInventory);
        existingInventory.setLocation(inventoryDTO.getLocation());
        existingInventory.setAddress(inventoryDTO.getAddress());
        logger.info("Inventory modificado: " + inventoryDTO);


        return inventoryRepository.save(existingInventory);
    }
}
