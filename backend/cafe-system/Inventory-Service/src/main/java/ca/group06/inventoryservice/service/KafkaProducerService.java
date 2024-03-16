package ca.group06.inventoryservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${topics.inventory-notification}")
    private String inventoryNotificationTopic;

    // ---------------------------------------------
    // ----------------- PRODUCERS -----------------
    // ---------------------------------------------

    public void sendDataToNotificationService(List<String> messages) {
        log.info("Sending data to {}", inventoryNotificationTopic);
        kafkaTemplate.send(inventoryNotificationTopic, messages);
    }

}
