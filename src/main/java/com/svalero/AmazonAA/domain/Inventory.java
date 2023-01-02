package com.svalero.AmazonAA.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @Size(min = 4, message = "La localizacion tiene que tener minimo 4 caracteres")
    private String location;

    @Column
    @NotNull(message = "La direccion es obligatoria")
    @NotBlank(message = "La direccion no puede estar en blanco")
    private String address;

    @Column
    private LocalDate lastUpdate;

    @Column
    private float totalValue;

    @ToString.Exclude
    @OneToMany(mappedBy = "productStock")
    @JsonBackReference(value = "productId_product")
    private List<Stock> productList;

}
