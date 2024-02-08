package ca.group06.batchservice.dto.type;

import lombok.Data;

@Data
public class CreateTypeRequest {
    private String name;
    private int storeDays;
}
