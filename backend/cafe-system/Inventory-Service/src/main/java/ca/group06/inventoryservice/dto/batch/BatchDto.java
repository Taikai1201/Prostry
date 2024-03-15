package ca.group06.inventoryservice.dto.batch;

import ca.group06.inventoryservice.dto.type.TypeInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class BatchDto {
    private UUID id;
    private String name;
    private TypeInfo type;
    private LocalDate createdAt;
    private LocalDate bestBefore;
    private int quantity;
    private UUID qrCodeId;
}
