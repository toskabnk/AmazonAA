package com.svalero.AmazonAA.domain;

import java.time.LocalDate;

public class Review {
    long id;
    Person customer;
    Product product;
    float rating;
    String comment;
    LocalDate date;
}
