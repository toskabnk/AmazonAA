package com.svalero.AmazonAA.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryDTO {
    @NotNull(message = "La localizacion es obligatoria")
    @NotBlank(message = "La localizacion no puede estar en blanco")
    @Size(min = 4, message = "La localizacion tiene que tener minimo 4 caracteres")
    String location;
    @NotNull(message = "La direccion es obligatoria")
    @NotBlank(message = "La direccion no puede estar en blanco")
    String address;
}
