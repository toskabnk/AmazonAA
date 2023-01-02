package com.svalero.AmazonAA.service;

import com.svalero.AmazonAA.domain.Person;
import com.svalero.AmazonAA.domain.Product;
import com.svalero.AmazonAA.domain.Review;
import com.svalero.AmazonAA.domain.dto.ModifyReviewDTO;
import com.svalero.AmazonAA.domain.dto.ReviewDTO;
import com.svalero.AmazonAA.exception.PersonNotFoundException;
import com.svalero.AmazonAA.exception.ProductNotFoundException;
import com.svalero.AmazonAA.exception.ReviewNotFoundException;
import com.svalero.AmazonAA.repository.PersonRepository;
import com.svalero.AmazonAA.repository.ProductRepository;
import com.svalero.AmazonAA.repository.ReviewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService{

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    ProductRepository productRepository;

    private final Logger logger = LoggerFactory.getLogger(ReviewServiceImpl.class);

    @Override
    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    @Override
    public Review findById(long id) throws ReviewNotFoundException {
        logger.info("ID Review: " + id);
        return reviewRepository.findById(id).orElseThrow(ReviewNotFoundException::new);
    }

    @Override
    public List<Review> findByCustomer(long custumerId) throws PersonNotFoundException{
        logger.info("ID Customer Review: " + custumerId);
        Person person = personRepository.findById(custumerId).orElseThrow(PersonNotFoundException::new);
        return reviewRepository.findByCustomerReview(person);
    }

    @Override
    public List<Review> findByProduct(long productId) throws  ProductNotFoundException{
        logger.info("ID Product Review: " + productId);
        Product product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
        return reviewRepository.findByProductReview(product);
    }

    @Override
    public Review addReview(ReviewDTO reviewDTO) throws PersonNotFoundException, ProductNotFoundException{
        logger.info("Review added: " + reviewDTO);
        Review review = new Review();
        Person customer = personRepository.findById(reviewDTO.getCustomerId()).orElseThrow(PersonNotFoundException::new);
        Product product = productRepository.findById(reviewDTO.getProductId()).orElseThrow(ProductNotFoundException::new);

        review.setCustomerReview(customer);
        review.setProductReview(product);
        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());
        review.setDate(LocalDate.now());

        return reviewRepository.save(review);
    }

    @Override
    public Review modifyReview(long id, ModifyReviewDTO modifyReviewDTO) throws ReviewNotFoundException {
        Review existingReview = reviewRepository.findById(id).orElseThrow(ReviewNotFoundException::new);
        logger.info("Existing Review: " + existingReview);
        logger.info("Modified Review: " + modifyReviewDTO);
        existingReview.setComment(modifyReviewDTO.getComment());
        existingReview.setRating(modifyReviewDTO.getRating());

        return reviewRepository.save(existingReview);
    }

    @Override
    public void deleteReview(long id) throws ReviewNotFoundException {
        Review existingReview = reviewRepository.findById(id).orElseThrow(ReviewNotFoundException::new);
        logger.info("Deleted Review: " + existingReview);
        reviewRepository.delete(existingReview);
    }
}
