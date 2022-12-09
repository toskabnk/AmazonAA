package com.svalero.AmazonAA.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotNull(message = "El nombre es obligatorio")
    @NotBlank(message = "El nombre no puede estar vacio")
    private String name;

    @Column
    @NotNull(message = "La descripcion es obligatoria")
    @NotBlank(message = "La descripcion no puede estar vacia")
    private String description;

    @Column
    @NotNull(message = "El precio es obligatorio")
    private float price;

    @Column
    private String category;

    @OneToMany(mappedBy = "inventoryStock")
    @JsonBackReference(value = "inventoryId_inventory")
    private List<Stock> inventories;

    @OneToMany(mappedBy = "productReview")
    @JsonBackReference(value = "productId_product")
    private List<Review> reviews;

}
