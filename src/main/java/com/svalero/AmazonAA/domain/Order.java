package com.svalero.AmazonAA.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @Min(value = 1)
    @NotNull(message = "La cantidad es obligatoria")
    private int quantity;

    @Column
    @NotNull
    private boolean paid;

    @Column
    private float totalPrice;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Person customer;
}
