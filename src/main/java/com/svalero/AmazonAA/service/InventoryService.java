package com.svalero.AmazonAA.service;

import com.svalero.AmazonAA.domain.Inventory;
import com.svalero.AmazonAA.domain.dto.InventoryDTO;
import com.svalero.AmazonAA.exception.InventoryNotFoundException;

import java.util.List;

public interface InventoryService {
    List<Inventory> findAll();
    Inventory findById(long id) throws InventoryNotFoundException;
    List<Inventory> findByLocation(String location);
    List<Inventory> findByAddress(String address);
    void updateInventory(long id) throws InventoryNotFoundException;
    Inventory addInventory(InventoryDTO inventoryDTO);
    boolean deleteInventory(long id) throws InventoryNotFoundException;
    Inventory modifyInventory(long id, InventoryDTO inventoryDTO) throws InventoryNotFoundException;
}
