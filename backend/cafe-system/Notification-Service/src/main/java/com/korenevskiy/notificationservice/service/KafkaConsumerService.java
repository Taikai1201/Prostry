package com.korenevskiy.notificationservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final NotificationService notificationService;

    private static final String inventoryNotificationTopic = "${topics.inventory-notification}";
    private static final String kafkaConsumerGroupId = "${spring.kafka.consumer.group-id}";

    @KafkaListener(
            topics = inventoryNotificationTopic,
            groupId = kafkaConsumerGroupId)
    public void sendNotificationsToClients(List<String> messages) {
        log.info("Received messages from {} topic", inventoryNotificationTopic);
        notificationService.sendEvents(messages);
    }

}
