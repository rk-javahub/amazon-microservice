package com.rkjavahub.notificationservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@Slf4j
public class NotificationServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApp.class, args);
    }

    @KafkaListener(topics = "notificationTopic",groupId = "notificationId")
    public void handleNotification(OrderPlacedEvent orderPlacedEvent) {
        // Add logic for send mail
        log.info("Notifiaction sent successfully for order id - {} ", orderPlacedEvent.getOrderId());
    }
}
