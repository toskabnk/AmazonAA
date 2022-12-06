package com.svalero.AmazonAA.repository;

import com.svalero.AmazonAA.domain.Review;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReviewRepository extends CrudRepository<Review, Long> {
    List<Review> findAll();
    Review findById();
    List<Review> findByProductReview();
}
