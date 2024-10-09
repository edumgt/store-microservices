package com.praveenukkoji.notificationservice;

import com.praveenukkoji.notificationservice.event.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
@EnableDiscoveryClient
@SpringBootApplication
public class NotificationserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationserviceApplication.class, args);
    }

    @KafkaListener(topics = "orderTopic")
    public void handleOrderNotification(OrderCreatedEvent orderCreatedEvent) {
        // send email notification
        log.info("ORDER NOTIFICATION ---- received notification for order id {}", orderCreatedEvent.getOrderId());
    }

}
