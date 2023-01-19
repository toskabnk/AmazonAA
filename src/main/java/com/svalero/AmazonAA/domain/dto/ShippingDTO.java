package com.svalero.AmazonAA.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShippingDTO {

    private String carrier;
    private LocalDate estimatedArrival;
    private String tracking;
    private String status;
    private long orderId;
}
