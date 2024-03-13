package ca.group06.batchservice.service;

import ca.group06.batchservice.dto.batch.SoldBatchUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaMessagingService {

    private final BatchService batchService;

    private static final String updateInventoryTopic = "${topics.update-inventory}";
    private static final String kafkaConsumerGroupId = "${spring.kafka.consumer.group-id}";

    @KafkaListener(topics = updateInventoryTopic, groupId = kafkaConsumerGroupId,
            properties = {"spring.json.value.default.type=ca.group06.batchservice.dto.batch.SoldBatchUpdateRequest"})
    public void updateRecordsWithSells(SoldBatchUpdateRequest message) {
        log.info("New message from ");
        batchService.updateRecordsWithSells(message);
    }

}
