package com.svalero.AmazonAA.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifyReviewDTO {
    @Min(value = 1, message = "La calificacion minima es 1")
    @Max(value = 5, message = "La calificacon maxima es 5")
    @NotNull(message = "La calificacion es obligatoria")
    private float rating;
    private String comment;
}
