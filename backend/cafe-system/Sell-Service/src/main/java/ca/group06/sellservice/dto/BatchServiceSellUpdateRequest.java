package ca.group06.sellservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BatchServiceSellUpdateRequest {
    List<SellInfo> sellInfos;

    @Data
    @Builder
    public static class SellInfo {
        private String soldItemName;
        private int quantity;
    }
}
