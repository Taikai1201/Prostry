package ca.group06.inventoryservice.dto.batch;

import lombok.Data;

import java.util.UUID;

@Data
public class UpdateBatchRequest {
    private String name;
    private int quantity;
    private UUID qrCodeId;
}
