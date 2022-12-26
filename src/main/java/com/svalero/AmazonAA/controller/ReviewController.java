package com.svalero.AmazonAA.controller;

import com.svalero.AmazonAA.domain.Review;
import com.svalero.AmazonAA.domain.dto.ModifyReviewDTO;
import com.svalero.AmazonAA.domain.dto.ReviewDTO;
import com.svalero.AmazonAA.exception.ErrorException;
import com.svalero.AmazonAA.exception.PersonNotFoundException;
import com.svalero.AmazonAA.exception.ProductNotFoundException;
import com.svalero.AmazonAA.exception.ReviewNotFoundException;
import com.svalero.AmazonAA.service.ReviewService;
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
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    private final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    @GetMapping("/reviews")
    public ResponseEntity<List<Review>> getReviews(@RequestParam Map<String,String> data) throws PersonNotFoundException, ProductNotFoundException, ReviewNotFoundException {
        logger.info("GET Review");
        if(data.isEmpty()){
            logger.info("END GET Review");
            return ResponseEntity.ok(reviewService.findAll());
        } else if(data.containsKey("customerId")){
            List<Review> reviews = reviewService.findByCustomer(Long.parseLong(data.get("customerId")));
            logger.info("END GET Review");
            return ResponseEntity.ok(reviews);
        } else if(data.containsKey("productId")){
            List<Review> reviews = reviewService.findByProduct(Long.parseLong(data.get("productId")));
            logger.info("END GET Review");
            return ResponseEntity.ok(reviews);
        } else if(data.containsKey("id")){
            List<Review> reviews = new ArrayList<>();
            reviews.add(reviewService.findById(Long.parseLong(data.get("id"))));
            logger.info("END GET Review");
            return ResponseEntity.ok(reviews);
        } else {
            logger.error("BAD REQUEST");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/reviews")
    public ResponseEntity<Review> addReview(@Valid @RequestBody ReviewDTO reviewDTO) throws PersonNotFoundException, ProductNotFoundException {
        logger.info("POST Review");
        Review review = reviewService.addReview(reviewDTO);
        logger.info("END POST Review");
        return ResponseEntity.status(HttpStatus.OK).body(review);
    }

    @PutMapping("/reviews/{id}")
    public ResponseEntity<Review> modifyReview(@PathVariable long id,@Valid @RequestBody ModifyReviewDTO modifyReviewDTO) throws ReviewNotFoundException {
        logger.info("PUT Review");
        Review review = reviewService.modifyReview(id, modifyReviewDTO);
        logger.info("END PUT Review");
        return ResponseEntity.status(HttpStatus.OK).body(review);
    }

    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable long id) throws ReviewNotFoundException{
        logger.info("DELETE Review");
        reviewService.deleteReview(id);
        logger.info("END DELETE Review");
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<ErrorException> handlePersonNotFoundException(PersonNotFoundException pnfe){
        logger.error("Person no encotrada");
        ErrorException errorException= new ErrorException(404, pnfe.getMessage());
        return new ResponseEntity<>(errorException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorException> handleProductNotFoundException(ProductNotFoundException pnfe){
        logger.error("Product no encotrado");
        ErrorException errorException= new ErrorException(404, pnfe.getMessage());
        return new ResponseEntity<>(errorException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    public  ResponseEntity<ErrorException> handleReviewNotFoundException(ReviewNotFoundException rnfe){
        logger.error("Review no encotrada");
        ErrorException errorException = new ErrorException(404, rnfe.getMessage());
        return new ResponseEntity<>(errorException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorException> handleConstraintViolationException(ConstraintViolationException cve){
        logger.error("Restricciones violadas");
        return getErrorExceptionResponseEntity(cve);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorException> handleException(Exception e){
        logger.error("Error interno " + e.getMessage());
        ErrorException error = new ErrorException(500, e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorException> handleMethodArgumentNotValidException(MethodArgumentNotValidException manve){
        logger.error("Datos introducidos erroneos");
        return getErrorExceptionResponseEntity(manve);
    }

}
