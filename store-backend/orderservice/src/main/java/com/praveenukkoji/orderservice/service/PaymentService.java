package com.praveenukkoji.orderservice.service;

import com.praveenukkoji.orderservice.dto.request.payment.MakePaymentRequest;
import com.praveenukkoji.orderservice.dto.response.payment.PaymentResponse;
import com.praveenukkoji.orderservice.exception.order.OrderNotFoundException;
import com.praveenukkoji.orderservice.exception.payment.CreatePaymentException;
import com.praveenukkoji.orderservice.exception.payment.PaymentNotFoundException;
import com.praveenukkoji.orderservice.model.Order;
import com.praveenukkoji.orderservice.model.Payment;
import com.praveenukkoji.orderservice.model.enums.OrderStatus;
import com.praveenukkoji.orderservice.model.enums.PaymentStatus;
import com.praveenukkoji.orderservice.repository.OrderRepository;
import com.praveenukkoji.orderservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final OrderRepository orderRepository;

    // make payment
    public UUID makePayment(MakePaymentRequest makePaymentRequest)
            throws OrderNotFoundException, CreatePaymentException {

        UUID orderId = makePaymentRequest.getOrderId();
        Optional<Order> order = orderRepository.findById(orderId);

        if (order.isPresent()) {

            Payment newPayment = Payment.builder()
                    .amount(makePaymentRequest.getAmount())
                    .order(order.get())
                    .build();

            switch (makePaymentRequest.getStatus().toUpperCase()) {
                case "SUCCESS":
                    newPayment.setStatus(PaymentStatus.SUCCESS);
                    newPayment.getOrder().setStatus(OrderStatus.PLACED);
                    break;
                case "FAILED":
                    newPayment.setStatus(PaymentStatus.FAILED);
                    break;
                default:
                    newPayment.setStatus(PaymentStatus.UNKNOWN);
                    break;
            }

            try {
                return paymentRepository.save(newPayment).getId();
            } catch (Exception e) {
                throw new CreatePaymentException(e.getMessage());
            }
        }

        throw new OrderNotFoundException("order with id = " + orderId + " not found");
    }

    // retrieve
    public PaymentResponse getPayment(UUID paymentId)
            throws PaymentNotFoundException {
        Optional<Payment> payment = paymentRepository.findById(paymentId);

        if (payment.isPresent()) {

            return PaymentResponse.builder()
                    .id(payment.get().getId())
                    .amount(payment.get().getAmount())
                    .status(String.valueOf(payment.get().getStatus()))
                    .orderId(payment.get().getOrder().getId())
                    .build();
        }

        throw new PaymentNotFoundException("payment with id = " + paymentId + " not found");
    }
}
