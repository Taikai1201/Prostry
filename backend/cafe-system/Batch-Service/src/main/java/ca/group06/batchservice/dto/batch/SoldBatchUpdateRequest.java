package ca.group06.batchservice.dto.batch;

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
