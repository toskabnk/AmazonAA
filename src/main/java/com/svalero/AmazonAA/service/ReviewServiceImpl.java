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

    @Override
    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    @Override
    public Review findById(long id) throws ReviewNotFoundException {
        return reviewRepository.findById(id).orElseThrow(ReviewNotFoundException::new);
    }

    @Override
    public List<Review> findByCustomer(long custumerId) throws PersonNotFoundException{
        Person person = personRepository.findById(custumerId).orElseThrow(PersonNotFoundException::new);
        return reviewRepository.findByCustomerReview(person);
    }

    @Override
    public List<Review> findByProduct(long productId) throws  ProductNotFoundException{
        Product product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
        return reviewRepository.findByProductReview(product);
    }

    @Override
    public Review addReview(ReviewDTO reviewDTO) throws PersonNotFoundException, ProductNotFoundException{
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
        existingReview.setComment(modifyReviewDTO.getComment());
        existingReview.setRating(modifyReviewDTO.getRating());

        return reviewRepository.save(existingReview);
    }

    @Override
    public void deleteReview(long id) throws ReviewNotFoundException {
        Review existingReview = reviewRepository.findById(id).orElseThrow(ReviewNotFoundException::new);
        reviewRepository.delete(existingReview);
    }
}
