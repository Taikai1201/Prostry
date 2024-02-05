package ca.group06.sellservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateSoldItemRequest {
    private String name;
    private int quantity;
    private BigDecimal price;
}
