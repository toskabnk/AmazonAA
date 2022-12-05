package com.svalero.AmazonAA.domain;

import java.time.LocalDate;
import java.util.List;

public class Inventory {
    long id;
    List<Product> productList;
    int lowStockThreshold;
    List<Product> lowStockProducts;
    LocalDate lastUpdate;
    float totalValue;


}
