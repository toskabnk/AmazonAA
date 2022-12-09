package com.svalero.AmazonAA.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Data
@Entity(name = "stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product productStock;

    @ManyToOne
    @JoinColumn(name = "inventory_id")
    private Inventory inventoryStock;

    @Column
    @Min(value = 0)
    private int quantity;
}
