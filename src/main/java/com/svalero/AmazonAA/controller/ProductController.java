package com.svalero.AmazonAA.controller;

import com.svalero.AmazonAA.domain.Product;
import com.svalero.AmazonAA.domain.dto.ProductDTO;
import com.svalero.AmazonAA.exception.ErrorException;
import com.svalero.AmazonAA.exception.ProductNotFoundException;
import com.svalero.AmazonAA.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static com.svalero.AmazonAA.util.ErrorExceptionUtil.getErrorExceptionResponseEntity;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(@RequestParam Map<String,String> data){
        if (data.isEmpty()) {
            return ResponseEntity.ok(productService.findAll());
        } else {
            if(data.containsKey("name")){
                List<Product> products = productService.findByNameContaining(data.get("name"));
                return ResponseEntity.ok(products);
            } else if(data.containsKey("category")){
                List<Product> products = productService.findByCategory(data.get("category"));
                return ResponseEntity.ok(products);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable long id) throws ProductNotFoundException {
        Product product = productService.findById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping("/products")
    public ResponseEntity<Product> addProduct(@Valid @RequestBody ProductDTO productDTO){
        Product newProduct = productService.addProduct(productDTO);
        return ResponseEntity.status(HttpStatus.OK).body(newProduct);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> modifyProduct(@PathVariable long id, @RequestBody Product product) throws ProductNotFoundException{
        Product newProduct = productService.modifyProduct(id, product);
        return ResponseEntity.status(HttpStatus.OK).body(newProduct);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deteleProduct(@PathVariable long id) throws ProductNotFoundException{
        productService.deteleProduct(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorException> handleProductNotFoundException(ProductNotFoundException pnfe){
        ErrorException errorException= new ErrorException(404, pnfe.getMessage());
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
