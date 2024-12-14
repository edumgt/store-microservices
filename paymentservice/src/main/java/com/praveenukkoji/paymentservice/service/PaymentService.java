package com.praveenukkoji.paymentservice.service;

import com.praveenukkoji.paymentservice.dto.payment.request.MakePaymentRequest;
import com.praveenukkoji.paymentservice.dto.payment.response.PaymentResponse;
import com.praveenukkoji.paymentservice.exception.error.ValidationException;
import com.praveenukkoji.paymentservice.exception.payment.CreatePaymentException;
import com.praveenukkoji.paymentservice.exception.payment.PaymentNotFoundException;
import com.praveenukkoji.events.PaymentEvent;
import com.praveenukkoji.paymentservice.model.Payment;
import com.praveenukkoji.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Transactional
@Slf4j
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    @Value("${spring.kafka.topic}")
    private String paymentTopic;

    // TODO: verify userId and addressId
    // create
    public UUID makePayment(MakePaymentRequest makePaymentRequest)
            throws CreatePaymentException, ValidationException {

        log.info("create payment request = {}", makePaymentRequest);

        Double amount = makePaymentRequest.getAmount();
        String paymentStatus = makePaymentRequest.getPaymentStatus().toLowerCase();
        String orderId = makePaymentRequest.getOrderId();

        if(amount <= 0) {
            throw new ValidationException("amount", "amount cannot be less than 1");
        }

        if(Objects.equals(paymentStatus, "")) {
            throw new ValidationException("paymentStatus", "payment status cannot be empty");
        }

        ArrayList<String> paymentStatusTypeList = new ArrayList<>(
                Arrays.asList("success", "failed", "processing"));

        if(!paymentStatusTypeList.contains(paymentStatus)) {
            throw new ValidationException("paymentStatus", "invalid payment status = " + paymentStatus);
        }

        if(Objects.equals(orderId, "")) {
            throw new ValidationException("orderId", "order id cannot be empty");
        }

        UUID paymentId = null;

        try {
            // creating new payment
            Payment newPayment = Payment.builder()
                    .amount(amount)
                    .paymentStatus(paymentStatus)
                    .orderId(orderId)
                    .build();

            paymentId = paymentRepository.save(newPayment).getId();

            // TODO: update order object accordingly
        }
        catch (Exception e) {
            throw new CreatePaymentException(e.getMessage());
        }

        // kafka sending payment created notification
        PaymentEvent paymentEvent = PaymentEvent.builder()
                .paymentId(String.valueOf(paymentId))
                .status(paymentStatus)
                .orderId(orderId)
                .message("payment created")
                .build();
        kafkaTemplate.send(paymentTopic, paymentEvent);

        return paymentId;
    }

    // get
    public PaymentResponse getPayment(String id)
            throws PaymentNotFoundException, ValidationException {

        log.info("fetching payment having id = {}", id);

        if(Objects.equals(id,"")) {
            throw new ValidationException("paymentId", "payment id cannot be empty");
        }

        UUID paymentId = UUID.fromString(id);

        Optional<Payment> payment = paymentRepository.findById(paymentId);

        if (payment.isPresent()) {
            return PaymentResponse.builder()
                    .id(payment.get().getId())
                    .amount(payment.get().getAmount())
                    .paymentStatus(payment.get().getPaymentStatus())
                    .orderId(payment.get().getOrderId())
                    .createdOn(payment.get().getCreatedOn())
                    .createdBy(payment.get().getCreatedBy())
                    .modifiedOn(payment.get().getModifiedOn())
                    .modifiedBy(payment.get().getModifiedBy())
                    .build();
        }

        throw new PaymentNotFoundException("payment with id = " + id + " not found");
    }
}
