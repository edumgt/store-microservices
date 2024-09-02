package com.praveenukkoji.orderservice.service;

import com.praveenukkoji.orderservice.dto.request.payment.MakePaymentRequest;
import com.praveenukkoji.orderservice.dto.response.payment.PaymentResponse;
import com.praveenukkoji.orderservice.exception.order.OrderNotFoundException;
import com.praveenukkoji.orderservice.exception.payment.CreatePaymentException;
import com.praveenukkoji.orderservice.exception.payment.PaymentNotFoundException;
import com.praveenukkoji.orderservice.model.Order;
import com.praveenukkoji.orderservice.model.Payment;
import com.praveenukkoji.orderservice.model.enums.PaymentStatus;
import com.praveenukkoji.orderservice.repository.OrderRepository;
import com.praveenukkoji.orderservice.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    public UUID makePayment(MakePaymentRequest makePaymentRequest)
            throws OrderNotFoundException, CreatePaymentException {

        Optional<Order> order = orderRepository.findById(makePaymentRequest.getOrderId());

        if (order.isPresent()) {

            Payment newPayment = Payment.builder()
                    .amount(makePaymentRequest.getAmount())
                    .order(order.get())
                    .build();

            switch (makePaymentRequest.getStatus().toUpperCase()) {
                case "SUCCESS":
                    newPayment.setStatus(PaymentStatus.SUCCESS);
                    break;
                case "FAILED":
                    newPayment.setStatus(PaymentStatus.FAILED);
                    break;
                default:
                    newPayment.setStatus(PaymentStatus.UNKNOWN);
                    break;
            }

            try {
                Payment payment = paymentRepository.saveAndFlush(newPayment);

                order.get().setPayment(payment);

                orderRepository.save(order.get());

                return payment.getId();
            } catch (Exception e) {
                throw new CreatePaymentException();
            }
        }

        throw new OrderNotFoundException();
    }

    public PaymentResponse getPayment(UUID paymentId)
            throws PaymentNotFoundException {
        Optional<Payment> payment = paymentRepository.findById(paymentId);

        if (payment.isPresent()) {

            return PaymentResponse.builder()
                    .id(payment.get().getId())
                    .amount(payment.get().getAmount())
                    .status(String.valueOf(payment.get().getStatus()))
                    .build();
        }

        throw new PaymentNotFoundException();
    }
}
