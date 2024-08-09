package com.praveenukkoji.orderservice.service;

import com.praveenukkoji.orderservice.dto.request.payment.MakePaymentRequest;
import com.praveenukkoji.orderservice.dto.response.payment.PaymentResponse;
import com.praveenukkoji.orderservice.exception.order.OrderNotFoundException;
import com.praveenukkoji.orderservice.exception.payment.CreatePaymentException;
import com.praveenukkoji.orderservice.exception.payment.PaymentNotFoundException;
import com.praveenukkoji.orderservice.model.Order;
import com.praveenukkoji.orderservice.model.Payment;
import com.praveenukkoji.orderservice.repository.OrderRepository;
import com.praveenukkoji.orderservice.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public PaymentResponse makePayment(MakePaymentRequest makePaymentRequest)
            throws OrderNotFoundException, CreatePaymentException {

        Optional<Order> order = orderRepository.findById(makePaymentRequest.getOrderId());

        if (order.isPresent()) {

            // TODO: OUT_FOR_DELIVERY, CASH_ON_DELIVERY, DISPATCHED

            if (Objects.equals(order.get().getStatus(), "PROCESSING")) {
                return PaymentResponse.builder()
                        .message("payment is processing")
                        .build();
            } else if (Objects.equals(order.get().getStatus(), "PLACED")) {
                return PaymentResponse.builder()
                        .message("already paid")
                        .build();
            } else if (Objects.equals(order.get().getStatus(), "DELIVERED")) {
                return PaymentResponse.builder()
                        .message("order delivered, you cannot pay")
                        .build();
            }

            Payment newPayment = Payment.builder()
                    .amount(makePaymentRequest.getAmount())
                    .status(makePaymentRequest.getStatus().toUpperCase())
                    .build();

            if (makePaymentRequest.getStatus().equalsIgnoreCase("FAILED")) {
                order.get().setStatus("CANCELED");
            } else if (makePaymentRequest.getStatus().equalsIgnoreCase("SUCCESS")) {
                order.get().setStatus("PLACED");
            } else {
                order.get().setStatus("PROCESSING");
            }

            order.get().setPayment(newPayment);

            try {
                UUID paymentId = paymentRepository.save(newPayment).getId();

                return PaymentResponse.builder()
                        .id(paymentId)
                        .build();
            } catch (Exception e) {
                throw new CreatePaymentException();
            }
        }

        throw new OrderNotFoundException();
    }

    public PaymentResponse getPayment(UUID paymentId) throws PaymentNotFoundException {

        Optional<Payment> payment = paymentRepository.findById(paymentId);

        if (payment.isPresent()) {
            return PaymentResponse.builder()
                    .id(payment.get().getId())
                    .amount(payment.get().getAmount())
                    .status(payment.get().getStatus())
                    .build();
        }

        throw new PaymentNotFoundException();
    }
}
