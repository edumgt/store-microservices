package com.praveenukkoji.orderservice.service;

import com.praveenukkoji.orderservice.dto.request.payment.MakePaymentRequest;
import com.praveenukkoji.orderservice.dto.response.payment.PaymentResponse;
import com.praveenukkoji.orderservice.exception.order.OrderNotFoundException;
import com.praveenukkoji.orderservice.exception.payment.CreatePaymentException;
import com.praveenukkoji.orderservice.exception.payment.PaymentNotFoundException;
import com.praveenukkoji.orderservice.exception.payment.PaymentStatusUpdateException;
import com.praveenukkoji.orderservice.kafka.order.OrderEvent;
import com.praveenukkoji.orderservice.model.Order;
import com.praveenukkoji.orderservice.model.Payment;
import com.praveenukkoji.orderservice.model.enums.OrderStatus;
import com.praveenukkoji.orderservice.model.enums.PaymentStatus;
import com.praveenukkoji.orderservice.repository.OrderRepository;
import com.praveenukkoji.orderservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@Slf4j
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final OrderRepository orderRepository;

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    // create
    public UUID makePayment(MakePaymentRequest makePaymentRequest)
            throws OrderNotFoundException, CreatePaymentException, PaymentStatusUpdateException {

        log.info("create payment request = {}", makePaymentRequest);

        UUID orderId = makePaymentRequest.getOrderId();
        Optional<Order> order = orderRepository.findById(orderId);

        UUID paymentId = null;

        if (order.isPresent()) {
            Payment newPayment = Payment.builder()
                    .amount(makePaymentRequest.getAmount())
                    .order(order.get())
                    .build();

            String paymentStatus = makePaymentRequest.getStatus();
            switch (paymentStatus.toUpperCase()) {
                case "SUCCESS":
                    newPayment.setStatus(PaymentStatus.SUCCESS);
                    newPayment.getOrder().setStatus(OrderStatus.PLACED);
                    break;
                case "FAILED":
                    newPayment.setStatus(PaymentStatus.FAILED);
                    newPayment.getOrder().setStatus(OrderStatus.PAYMENT_FAILED);
                    break;
                default:
                    throw new PaymentStatusUpdateException("unknown status = " + paymentStatus);
            }

            try {
                paymentId = paymentRepository.save(newPayment).getId();
            } catch (Exception e) {
                throw new CreatePaymentException(e.getMessage());
            }

            // kafka sending payment notification
            OrderEvent orderEvent = OrderEvent.builder()
                    .title("Payment Created")
                    .message("payment created with status = " + paymentStatus.toUpperCase() + " for order id = " + orderId)
                    .build();
            kafkaTemplate.send("orderTopic", orderEvent);

            return paymentId;
        }

        throw new OrderNotFoundException("order with id = " + orderId + " not found");
    }

    // get
    public PaymentResponse getPayment(UUID paymentId)
            throws PaymentNotFoundException {

        log.info("fetching payment having id = {}", paymentId);

        Optional<Payment> payment = paymentRepository.findById(paymentId);

        if (payment.isPresent()) {
            String paymentStatus = String.valueOf(payment.get().getStatus());

            return PaymentResponse.builder()
                    .id(payment.get().getId())
                    .amount(payment.get().getAmount())
                    .status(paymentStatus)
                    .orderId(payment.get().getOrder().getId())
                    .build();
        }

        throw new PaymentNotFoundException("payment with id = " + paymentId + " not found");
    }
}
