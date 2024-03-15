package ca.group06.inventoryservice.dto.batch;

import lombok.Data;

import java.util.List;

@Data
public class SoldBatchUpdateRequest {
    List<SellInfo> sellInfos;

    @Data
    public static class SellInfo {
        private String soldItemName;
        private int quantity;
    }
}
