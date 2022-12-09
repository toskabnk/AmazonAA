package com.svalero.AmazonAA.service;

import com.svalero.AmazonAA.domain.Review;
import com.svalero.AmazonAA.domain.dto.ModifyReviewDTO;
import com.svalero.AmazonAA.domain.dto.ReviewDTO;
import com.svalero.AmazonAA.exception.PersonNotFoundException;
import com.svalero.AmazonAA.exception.ProductNotFoundException;
import com.svalero.AmazonAA.exception.ReviewNotFoundException;

import java.util.List;

public interface ReviewService {
    List<Review> findAll();
    Review findById(long id) throws ReviewNotFoundException;
    List<Review> findByCustomer(long custumerId) throws PersonNotFoundException;
    List<Review> findByProduct(long productId) throws ProductNotFoundException;

    Review addReview(ReviewDTO reviewDTO) throws PersonNotFoundException, ProductNotFoundException;
    Review modifyReview(long id, ModifyReviewDTO modifyReviewDTO) throws ReviewNotFoundException;
    void deleteReview(long id) throws ReviewNotFoundException;


}
