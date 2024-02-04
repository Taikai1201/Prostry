package ca.group06.batchservice.dto.type;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@Builder
@RequiredArgsConstructor
public class TypeInfo {
    private UUID id;
    private String name;
    private int storeDays;
}
