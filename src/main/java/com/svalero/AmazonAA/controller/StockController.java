package com.svalero.AmazonAA.controller;

import com.svalero.AmazonAA.domain.Stock;
import com.svalero.AmazonAA.domain.dto.StockDTO;
import com.svalero.AmazonAA.exception.ErrorException;
import com.svalero.AmazonAA.exception.InventoryNotFoundException;
import com.svalero.AmazonAA.exception.ProductNotFoundException;
import com.svalero.AmazonAA.exception.StockNotFoundException;
import com.svalero.AmazonAA.service.InventoryService;
import com.svalero.AmazonAA.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Map;

import static com.svalero.AmazonAA.util.ErrorExceptionUtil.getErrorExceptionResponseEntity;

@RestController
public class StockController {

    @Autowired
    StockService stockService;

    @Autowired
    InventoryService inventoryService;

    @GetMapping("/stocks")
    public ResponseEntity<List<Stock>> getStocks(@RequestParam Map<String, String> data){
        if(data.isEmpty()){
            return ResponseEntity.ok(stockService.findAll());
        } else if(data.containsKey("productId")){
            return ResponseEntity.ok(stockService.findByProduct(Long.parseLong(data.get("productId"))));
        } else if(data.containsKey("inventoryId")){
            return ResponseEntity.ok(stockService.findByInventory(Long.parseLong(data.get("inventoryId"))));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/stocks")
    public ResponseEntity<Stock> addStock(@RequestBody StockDTO stockDTO) throws InventoryNotFoundException, ProductNotFoundException {
        Stock stock1 = stockService.addStock(stockDTO);
        inventoryService.updateInventory(stock1.getInventoryStock().getId());
        return ResponseEntity.status(HttpStatus.OK).body(stock1);
    }

    @PutMapping("/stocks/{id}")
    public ResponseEntity<Stock> modifyStock(@PathVariable long id, @RequestBody StockDTO stockDTO) throws InventoryNotFoundException, StockNotFoundException, ProductNotFoundException {
        Stock stock1 = stockService.modifyStock(id, stockDTO);
        inventoryService.updateInventory(stock1.getInventoryStock().getId());
        return ResponseEntity.status(HttpStatus.OK).body(stock1);
    }

    @DeleteMapping("/stocks/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable long id) throws StockNotFoundException, InventoryNotFoundException {
        Stock stock = stockService.findById(id);
        stockService.deleteStock(id);
        inventoryService.updateInventory(stock.getInventoryStock().getId());
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ExceptionHandler(InventoryNotFoundException.class)
    public ResponseEntity<ErrorException> handleInventoryNotFoundException(InventoryNotFoundException infe){
        ErrorException errorException = new ErrorException(404, infe.getMessage());
        return new ResponseEntity<>(errorException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorException> handleProductNotFoundException(ProductNotFoundException pnfe){
        ErrorException errorException= new ErrorException(404, pnfe.getMessage());
        return new ResponseEntity<>(errorException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StockNotFoundException.class)
    public ResponseEntity<ErrorException> handleStockNotFoundException(StockNotFoundException snfe){
        ErrorException errorException = new ErrorException(404, snfe.getMessage());
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
