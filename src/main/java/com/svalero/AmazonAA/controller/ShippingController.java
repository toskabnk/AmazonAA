package com.svalero.AmazonAA.controller;

import com.svalero.AmazonAA.domain.Shipping;
import com.svalero.AmazonAA.domain.dto.ShippingDTO;
import com.svalero.AmazonAA.exception.ErrorException;
import com.svalero.AmazonAA.exception.OrderNotFoundException;
import com.svalero.AmazonAA.exception.ShippingNotFoundException;
import com.svalero.AmazonAA.exception.ShippingWithOrderAlreadyExist;
import com.svalero.AmazonAA.service.ShippingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static com.svalero.AmazonAA.util.ErrorExceptionUtil.getErrorExceptionResponseEntity;

@RestController
public class ShippingController {

    @Autowired
    private ShippingService shippingService;

    private final Logger logger = LoggerFactory.getLogger(ShippingController.class);

    @GetMapping("/shipping")
    public ResponseEntity<List<Shipping>> getShipping(@RequestParam Map<String, String> data){
        if(data.isEmpty()){
            return ResponseEntity.ok(shippingService.findAll());
        } else {
            if(data.containsKey("carrier")){
                List<Shipping> shippings = shippingService.findByCarrierContainingIgnoreCase(data.get("carrier"));
                return ResponseEntity.ok(shippings);
            }
        }
        logger.error("Bad Request");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/shipping")
    public ResponseEntity<Shipping> addShipping(@Valid @RequestBody ShippingDTO shippingDTO) throws OrderNotFoundException, ShippingWithOrderAlreadyExist {
        Shipping shipping = shippingService.addShipping(shippingDTO);
        return  ResponseEntity.status(HttpStatus.OK).body(shipping);
    }

    @PutMapping("/shipping/{id}")
    public ResponseEntity<Shipping> modifyShipping(@PathVariable long id, @Valid @RequestBody ShippingDTO shippingDTO) throws ShippingNotFoundException, OrderNotFoundException {
        Shipping newShipping = shippingService.modifyShipping(id, shippingDTO);
        return ResponseEntity.status(HttpStatus.OK).body(newShipping);
    }

    @DeleteMapping("/shipping/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable long id) throws ShippingNotFoundException{
        shippingService.deleteShipping(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(ShippingNotFoundException.class)
    public ResponseEntity<ErrorException> handleShippingNotFoundException(ShippingNotFoundException snfe){
        logger.error("Shipping no encontrada");
        ErrorException errorException= new ErrorException(404, snfe.getMessage());
        return new ResponseEntity<>(errorException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ShippingWithOrderAlreadyExist.class)
    public ResponseEntity<ErrorException> handleShippingWithOrderAlreadyExist(ShippingWithOrderAlreadyExist swoae){
        logger.error("Shipping con Order ya existente");
        ErrorException errorException= new ErrorException(403, swoae.getMessage());
        return new ResponseEntity<>(errorException, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorException> handleOrderNotFoundException(OrderNotFoundException onfe){
        logger.error("Order no encontrada");
        ErrorException errorException= new ErrorException(404, onfe.getMessage());
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorException> handleMethodArgumentNotValidException(MethodArgumentNotValidException manve){
        logger.error("Datos introducidos erroneos");
        return getErrorExceptionResponseEntity(manve);
    }

}
