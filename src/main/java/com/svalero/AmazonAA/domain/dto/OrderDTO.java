package com.svalero.AmazonAA.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    @Min(value = 1)
    @NotNull(message = "La cantidad es obligatoria")
    private int quantity;
    @NotNull
    private boolean paid;
    private long productId;
    private long customerId;
}
