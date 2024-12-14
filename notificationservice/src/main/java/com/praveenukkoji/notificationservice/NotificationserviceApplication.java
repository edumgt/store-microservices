package com.praveenukkoji.notificationservice;

import com.praveenukkoji.events.OrderEvent;
import com.praveenukkoji.events.PaymentEvent;
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

    @KafkaListener(topics = "orderTopic", groupId = "notification-service")
    public void handleOrderNotification(OrderEvent orderEvent) {
        log.info("ORDER-SERVICE NOTIFICATION ---- received notification : {message: {}}", orderEvent.getMessage());

        // send email notification
        String body = orderEvent.getMessage() + " with status : " + orderEvent.getStatus()
                + " with id : " + orderEvent.getOrderId();
        emailService.sendEmail(to, orderEvent.getMessage(), body);
    }

    @KafkaListener(topics = "paymentTopic", groupId = "notification-service")
    public void handlePaymentNotification(PaymentEvent paymentEvent) {
        log.info("PAYMENT-SERVICE NOTIFICATION ---- received notification  {message: {}}", paymentEvent.getMessage());

        // send email notification
        String body = paymentEvent.getMessage() + " with status : " + paymentEvent.getStatus()
                + " with id : " + paymentEvent.getPaymentId();
        emailService.sendEmail(to, paymentEvent.getMessage(), body);
    }
}
