package com.svalero.AmazonAA.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockDTO {
    private long productStock;
    private long inventoryStock;
    private int quantity;
}
