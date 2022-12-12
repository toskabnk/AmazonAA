package com.svalero.AmazonAA.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "customder_id")
    Person customerReview;


    @NotNull
    @ManyToOne
    @JoinColumn(name = "product_id")
    Product productReview;

    @Column
    @Min(value = 1, message = "La calificacion minima es 1")
    @Max(value = 5, message = "La calificacon maxima es 5")
    @NotNull(message = "La calificacion es obligatoria")
    float rating;

    @Column
    String comment;

    @Column
    LocalDate date;
}
