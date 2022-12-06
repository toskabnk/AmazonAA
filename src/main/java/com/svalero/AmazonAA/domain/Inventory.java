package com.svalero.AmazonAA.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotNull(message = "La localizacion es obligatoria")
    @NotBlank(message = "La localizacion no puede estar en blanco")
    private String location;

    @Column
    private LocalDate lastUpdate;

    @Column
    private float totalValue;

    @OneToMany(mappedBy = "productStock")
    private List<Stock> productList;

}
