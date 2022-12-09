package com.svalero.AmazonAA.controller;

import com.svalero.AmazonAA.domain.Inventory;
import com.svalero.AmazonAA.domain.dto.InventoryDTO;
import com.svalero.AmazonAA.exception.ErrorException;
import com.svalero.AmazonAA.exception.InventoryNotFoundException;
import com.svalero.AmazonAA.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.svalero.AmazonAA.util.ErrorExceptionUtil.getErrorExceptionResponseEntity;

@RestController
public class InventoryController {

    @Autowired
    InventoryService inventoryService;

    @GetMapping("/inventories")
    public ResponseEntity<List<Inventory>> getInventories(@RequestParam Map<String, String> data) throws InventoryNotFoundException {
        if(data.isEmpty()){
            return ResponseEntity.ok(inventoryService.findAll());
        } else if(data.containsKey("location")){
            List<Inventory> inventories = inventoryService.findByLocation(data.get("location"));
            return ResponseEntity.ok(inventories);
        } else if(data.containsKey("address")){
            List<Inventory> inventories = inventoryService.findByAddress(data.get("address"));
            return ResponseEntity.ok(inventories);
        } else if(data.containsKey("id")){
            List<Inventory> inventories = new ArrayList<>();
            inventories.add(inventoryService.findById(Long.parseLong(data.get("id"))));
            return ResponseEntity.ok(inventories);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/inventories")
    public ResponseEntity<Inventory> addInventory(@Valid @RequestBody InventoryDTO inventoryDTO){
        Inventory inventory = inventoryService.addInventory(inventoryDTO);
        return ResponseEntity.status(HttpStatus.OK).body(inventory);
    }

    @PutMapping("/inventories/{id}")
    public ResponseEntity<Inventory> modifyInventory(@PathVariable long id, @RequestBody InventoryDTO inventoryDTO) throws InventoryNotFoundException{
        Inventory inventory = inventoryService.modifyInventory(id, inventoryDTO);
        return ResponseEntity.status(HttpStatus.OK).body(inventory);
    }

    @DeleteMapping("inventories/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable long id) throws InventoryNotFoundException{
        inventoryService.deleteInventory(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(InventoryNotFoundException.class)
    public ResponseEntity<ErrorException> handleInventoryNotFoundException(InventoryNotFoundException infe){
        ErrorException errorException = new ErrorException(404, infe.getMessage());
        return new ResponseEntity<>(errorException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorException> handleConstraintViolationException(ConstraintViolationException cve){
        return getErrorExceptionResponseEntity(cve);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorException> handleException(Exception e){
        //Borrar el mensaje del error 500 luego
        ErrorException error = new ErrorException(500, e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
