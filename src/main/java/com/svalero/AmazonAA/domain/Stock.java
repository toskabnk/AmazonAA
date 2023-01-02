package com.svalero.AmazonAA.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Data
@Entity(name = "stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product productStock;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "inventory_id")
    private Inventory inventoryStock;

    @Column
    @Min(value = 0)
    private int quantity;
}
