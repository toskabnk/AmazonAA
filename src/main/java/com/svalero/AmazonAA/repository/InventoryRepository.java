package com.svalero.AmazonAA.repository;

import com.svalero.AmazonAA.domain.Inventory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface InventoryRepository extends CrudRepository<Inventory, Long> {
    List<Inventory> findAll();
    List<Inventory> findByAddressContainingIgnoreCase(String address);
    List<Inventory> findByLocationContainingIgnoreCase(String location);
}
