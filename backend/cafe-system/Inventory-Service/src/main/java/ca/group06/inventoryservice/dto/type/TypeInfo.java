package ca.group06.inventoryservice.dto.type;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class TypeInfo {
    private UUID id;
    private String name;
    private int storeDays;
}
