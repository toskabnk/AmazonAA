package com.svalero.AmazonAA.controller;

import com.svalero.AmazonAA.domain.Inventory;
import com.svalero.AmazonAA.domain.InventoryMapDTO;
import com.svalero.AmazonAA.domain.dto.InventoryDTO;
import com.svalero.AmazonAA.exception.ErrorException;
import com.svalero.AmazonAA.exception.InventoryNotFoundException;
import com.svalero.AmazonAA.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    private final Logger logger = LoggerFactory.getLogger(InventoryController.class);

    @GetMapping("/inventories")
    public ResponseEntity<List<Inventory>> getInventories(@RequestParam Map<String, String> data) throws InventoryNotFoundException {
        logger.info("GET Inventories");
        if(data.isEmpty()){
            logger.info("END GET Inventories");
            return ResponseEntity.ok(inventoryService.findAll());
        } else if(data.containsKey("location")){
            String debug = "Data:" + "location " + data.get("location") ;
            logger.info(debug);
            List<Inventory> inventories = inventoryService.findByLocation(data.get("location"));
            logger.info("END GET Inventories");
            return ResponseEntity.ok(inventories);
        } else if(data.containsKey("address")){
            String debug = "Data:" + "address " + data.get("address") ;
            logger.info(debug);
            List<Inventory> inventories = inventoryService.findByAddress(data.get("address"));
            logger.info("END GET Inventories");
            return ResponseEntity.ok(inventories);
        } else if(data.containsKey("id")){
            String debug = "Data:" + "id " + data.get("id") ;
            logger.info(debug);
            List<Inventory> inventories = new ArrayList<>();
            inventories.add(inventoryService.findById(Long.parseLong(data.get("id"))));
            logger.info("END GET Inventories");
            return ResponseEntity.ok(inventories);
        }
        logger.info("GET Inventories: BAD REQUEST");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/inventories")
    public ResponseEntity<Inventory> addInventory(@Valid @RequestBody InventoryDTO inventoryDTO){
        logger.info("POST Inventories");
        Inventory inventory = inventoryService.addInventory(inventoryDTO);
        logger.info("END POST Inventories");
        return ResponseEntity.status(HttpStatus.OK).body(inventory);
    }

    @PostMapping("/inventories/{id}/waypoint")
    public ResponseEntity<Inventory> addWaypointToInventory(@PathVariable long id,@RequestBody InventoryMapDTO inventoryMapDTO) throws InventoryNotFoundException {
        logger.info("POST Inventories Waypoint");
        Inventory inventory = inventoryService.addWaypointToInventory(id, inventoryMapDTO);
        logger.info("END POST Inventories Waypoint");
        return ResponseEntity.status(HttpStatus.OK).body(inventory);
    }

    @PutMapping("/inventories/{id}")
    public ResponseEntity<Inventory> modifyInventory(@PathVariable long id,@Valid @RequestBody InventoryDTO inventoryDTO) throws InventoryNotFoundException{
        logger.error("PUT Inventories");
        Inventory inventory = inventoryService.modifyInventory(id, inventoryDTO);
        logger.error("END PUT Inventories");
        return ResponseEntity.status(HttpStatus.OK).body(inventory);
    }

    @DeleteMapping("inventories/{id}")
    public ResponseEntity<ErrorException> deleteInventory(@PathVariable long id) throws InventoryNotFoundException{
        logger.error("DELETE Inventories");
        boolean result = inventoryService.deleteInventory(id);
        logger.error("END DELETE Inventories");
        if(result){
            return ResponseEntity.noContent().build();
        } else {
        ErrorException error = new ErrorException(403, "El borrado no se ha permitido.");
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }


    }

    @ExceptionHandler(InventoryNotFoundException.class)
    public ResponseEntity<ErrorException> handleInventoryNotFoundException(InventoryNotFoundException infe){
        logger.error("Inventary no encontrado");
        ErrorException errorException = new ErrorException(404, infe.getMessage());
        return new ResponseEntity<>(errorException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorException> handleConstraintViolationException(ConstraintViolationException cve){
        logger.error("Restricciones violadas");
        return getErrorExceptionResponseEntity(cve);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorException> handleException(Exception e){
        logger.error("Error Interno ", e.getMessage());
        ErrorException error = new ErrorException(500, "Ha habido algun error inesperado");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorException> handleMethodArgumentNotValidException(MethodArgumentNotValidException manve){
        logger.error("Datos introducidos erroneos");
        return getErrorExceptionResponseEntity(manve);
    }
}
