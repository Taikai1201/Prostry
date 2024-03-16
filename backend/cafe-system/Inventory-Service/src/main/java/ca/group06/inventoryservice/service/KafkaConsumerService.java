package ca.group06.inventoryservice.service;

import ca.group06.inventoryservice.dto.batch.SoldBatchUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final BatchService batchService;

    private static final String kafkaConsumerGroupId = "${spring.kafka.consumer.group-id}";
    private static final String updateInventoryTopic = "${topics.update-inventory}";

    // ---------------------------------------------
    // ----------------- CONSUMERS -----------------
    // ---------------------------------------------
    @KafkaListener(topics = updateInventoryTopic, groupId = kafkaConsumerGroupId,
            properties = {"spring.json.value.default.type=ca.group06.batchservice.dto.batch.SoldBatchUpdateRequest"},
            autoStartup = "false")
    public void updateRecordsWithSells(SoldBatchUpdateRequest message) {
        log.info("New message from {}", updateInventoryTopic);
        batchService.updateRecordsWithSells(message);
    }

}
