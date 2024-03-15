package ca.group06.inventoryservice.dto.batch;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class CreateBatchRequest {
    private String name;
    private UUID typeId;
    private int quantity;
    private UUID qrCodeId;
}
