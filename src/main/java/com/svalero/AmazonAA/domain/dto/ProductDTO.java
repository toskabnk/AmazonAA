package com.svalero.AmazonAA.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    @NotNull(message = "El nombre es obligatorio")
    @NotBlank(message = "El nombre no puede estar vacio")
    private String name;
    @NotNull(message = "La descripcion es obligatoria")
    @NotBlank(message = "La descripcion no puede estar vacia")
    private String description;
    @NotNull(message = "El precio es obligatorio")
    private float price;
    private String category;
}
