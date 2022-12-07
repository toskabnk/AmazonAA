package com.svalero.AmazonAA.repository;

import com.svalero.AmazonAA.domain.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {
    List<Review> findAll();
}
