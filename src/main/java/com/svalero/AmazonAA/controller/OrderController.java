package com.svalero.AmazonAA.controller;

import com.svalero.AmazonAA.domain.Order;
import com.svalero.AmazonAA.domain.Person;
import com.svalero.AmazonAA.domain.Product;
import com.svalero.AmazonAA.domain.dto.OrderDTO;
import com.svalero.AmazonAA.domain.dto.PersonDTO;
import com.svalero.AmazonAA.exception.ErrorException;
import com.svalero.AmazonAA.exception.OrderNotFoundException;
import com.svalero.AmazonAA.exception.PersonNotFoundException;
import com.svalero.AmazonAA.exception.ProductNotFoundException;
import com.svalero.AmazonAA.service.OrderService;
import com.svalero.AmazonAA.service.PersonService;
import com.svalero.AmazonAA.service.ProductService;
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
public class OrderController {


    @Autowired
    private OrderService orderService;

    @Autowired
    private PersonService personService;

    @Autowired
    private ProductService productService;

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getOrders(@RequestParam Map<String,String> data) throws PersonNotFoundException, ProductNotFoundException {
        if (data.isEmpty()) {
            return ResponseEntity.ok(orderService.findAll());
        } else {
            if(data.containsKey("username")){
                List<Order> orders = orderService.findByPersonUsername(data.get("username"));
                return ResponseEntity.ok(orders);
            } else if(data.containsKey("product")){
                List<Order> orders = orderService.findByProductId(Long.parseLong(data.get("product")));
                return ResponseEntity.ok(orders);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable long id) throws OrderNotFoundException {
        Order order = orderService.findById(id);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/orders")
    public ResponseEntity<Order> addOrder(@Valid @RequestBody OrderDTO orderDTO) throws PersonNotFoundException, ProductNotFoundException {
        Order newOrder = orderService.addOrder(orderDTO);
        return ResponseEntity.status(HttpStatus.OK).body(newOrder);
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<Order> modifyOrder(@PathVariable long id, @RequestBody OrderDTO orderDTO) throws OrderNotFoundException, ProductNotFoundException{
        Order newOrder = orderService.modifyOrder(id, orderDTO);
        return ResponseEntity.status(HttpStatus.OK).body(newOrder);
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable long id) throws OrderNotFoundException{
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<ErrorException> handlePersonNotFoundException(PersonNotFoundException pnfe){
        ErrorException errorException= new ErrorException(404, pnfe.getMessage());
        return new ResponseEntity<>(errorException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorException> handleProductNotFoundException(ProductNotFoundException pnfe){
        ErrorException errorException= new ErrorException(404, pnfe.getMessage());
        return new ResponseEntity<>(errorException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorException> handleOrderNotFoundException(OrderNotFoundException onfe){
        ErrorException errorException= new ErrorException(404, onfe.getMessage());
        return new ResponseEntity<>(errorException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorException> handleConstraintViolationException(ConstraintViolationException cve){
        return getErrorExceptionResponseEntity(cve);
    }

}
