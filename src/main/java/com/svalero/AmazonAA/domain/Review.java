package com.svalero.AmazonAA.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne
    @JoinColumn(name = "customder_id")
    Person customerReview;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product productReview;

    @Column
    @NotNull(message = "La calificacion es obligatoria")
    @NotBlank(message = "La calificacion no puede estar vacia")
    float rating;

    @Column
    String comment;

    @Column
    LocalDate date;
}
