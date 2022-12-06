package com.svalero.AmazonAA.repository;

import com.svalero.AmazonAA.domain.Inventory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InventoryRepository extends CrudRepository<Inventory, Long> {
    List<Inventory> findAll();
    Inventory findById();
}
