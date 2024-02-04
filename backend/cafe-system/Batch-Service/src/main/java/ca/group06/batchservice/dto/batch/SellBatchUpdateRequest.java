package ca.group06.batchservice.dto.batch;

import lombok.Data;

import java.util.List;

@Data
public class SellBatchUpdateRequest {
    List<SellInfo> sellInfos;

    @Data
    public class SellInfo {
        private String soldItemName;
        private int quantity;
    }
}
