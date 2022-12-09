package com.svalero.AmazonAA.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockDTO {
    private long productStock;
    private long inventoryStock;
    private int quantity;
}
