package com.svalero.AmazonAA.domain;

import java.util.Map;

public class Order {
    long id;
    Product product;
    int quantity;
    boolean paid;
    float totalPrice;
    Person customer;
}
