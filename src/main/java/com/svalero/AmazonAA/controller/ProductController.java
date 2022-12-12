package com.svalero.AmazonAA.controller;

import com.svalero.AmazonAA.domain.Product;
import com.svalero.AmazonAA.domain.dto.ProductDTO;
import com.svalero.AmazonAA.exception.ErrorException;
import com.svalero.AmazonAA.exception.ProductNotFoundException;
import com.svalero.AmazonAA.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(@RequestParam Map<String,String> data){
        logger.info("GET Products");
        if (data.isEmpty()) {
            logger.info("END GET Products");
            return ResponseEntity.ok(productService.findAll());
        } else {
            if(data.containsKey("name")){
                List<Product> products = productService.findByNameContaining(data.get("name"));
                logger.info("END GET Products");
                return ResponseEntity.ok(products);
            } else if(data.containsKey("category")){
                List<Product> products = productService.findByCategory(data.get("category"));
                logger.info("END GET Products");
                return ResponseEntity.ok(products);
            }
        }
        logger.error("BAD REQUEST");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable long id) throws ProductNotFoundException {
        logger.info("GET Products");
        Product product = productService.findById(id);
        logger.info("END GET Products");
        return ResponseEntity.ok(product);
    }

    @PostMapping("/products")
    public ResponseEntity<Product> addProduct(@Valid @RequestBody ProductDTO productDTO){
        logger.info("POST Products");
        Product newProduct = productService.addProduct(productDTO);
        logger.info("END POST Products");
        return ResponseEntity.status(HttpStatus.OK).body(newProduct);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> modifyProduct(@PathVariable long id, @RequestBody Product product) throws ProductNotFoundException{
        logger.info("PUT Products");
        Product newProduct = productService.modifyProduct(id, product);
        logger.info("END PUT Products");
        return ResponseEntity.status(HttpStatus.OK).body(newProduct);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deteleProduct(@PathVariable long id) throws ProductNotFoundException{
        logger.info("DELETE Products");
        productService.deteleProduct(id);
        logger.info("END DELETE Products");
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorException> handleProductNotFoundException(ProductNotFoundException pnfe){
        logger.error("Product no encontrado");
        ErrorException errorException= new ErrorException(404, pnfe.getMessage());
        return new ResponseEntity<>(errorException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorException> handleConstraintViolationException(ConstraintViolationException cve){
        logger.error("Restricciones violadas");
        return getErrorExceptionResponseEntity(cve);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorException> handleException(Exception e){
        logger.error("Error Interno " + e.getMessage());
        ErrorException error = new ErrorException(500, e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
