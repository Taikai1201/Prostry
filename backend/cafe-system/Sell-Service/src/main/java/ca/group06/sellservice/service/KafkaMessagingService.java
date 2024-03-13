package ca.group06.sellservice.service;

import ca.group06.sellservice.dto.BatchServiceSellsUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaMessagingService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${topics.update-inventory}")
    private String updateInventoryTopic;

    public void sendUpdateMessageToBatchService(BatchServiceSellsUpdateRequest messageBody) {
        log.info("Sending update info to batch service");
        kafkaTemplate.send(updateInventoryTopic, messageBody);
    }

}
