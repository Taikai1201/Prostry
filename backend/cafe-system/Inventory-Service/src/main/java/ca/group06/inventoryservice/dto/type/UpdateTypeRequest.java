package ca.group06.inventoryservice.dto.type;

import lombok.Data;

@Data
public class UpdateTypeRequest {
    private String name;
    private int storeDays;
}
