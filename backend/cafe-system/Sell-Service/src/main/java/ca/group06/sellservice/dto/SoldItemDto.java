package ca.group06.sellservice.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class SoldItemDto {
    private UUID id;
    private String name;
    private int quantity;
    private LocalDate soldAt;
    private BigDecimal price;
}
