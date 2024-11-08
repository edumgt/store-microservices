package com.praveenukkoji.notificationservice;

import com.praveenukkoji.notificationservice.event.OrderEvent;
import com.praveenukkoji.notificationservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
@RequiredArgsConstructor
@EnableDiscoveryClient
@SpringBootApplication
public class NotificationserviceApplication {

    private final EmailService emailService;

    @Value("${spring.mail.to}")
    private String to;

    public static void main(String[] args) {
        SpringApplication.run(NotificationserviceApplication.class, args);
    }

    @KafkaListener(topics = "orderTopic")
    public void handleOrderNotification(OrderEvent orderEvent) {
        // send email notification
        log.info("ORDER NOTIFICATION ---- received notification title: {}, message: {}",
                orderEvent.getTitle(), orderEvent.getMessage());

        emailService.sendEmail(to, orderEvent.getTitle(), orderEvent.getMessage());
    }

}
