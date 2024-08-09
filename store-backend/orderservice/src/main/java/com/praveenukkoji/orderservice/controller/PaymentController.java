package com.praveenukkoji.orderservice.controller;

import com.praveenukkoji.orderservice.dto.error.ErrorResponse;
import com.praveenukkoji.orderservice.dto.request.payment.MakePaymentRequest;
import com.praveenukkoji.orderservice.exception.order.OrderNotFoundException;
import com.praveenukkoji.orderservice.exception.payment.CreatePaymentException;
import com.praveenukkoji.orderservice.exception.payment.PaymentNotFoundException;
import com.praveenukkoji.orderservice.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping(path = "/make-payment")
    public ResponseEntity<?> makePayment(@RequestBody @Valid MakePaymentRequest makePaymentRequest)
            throws OrderNotFoundException, CreatePaymentException {

        return ResponseEntity.status(201).body(paymentService.makePayment(makePaymentRequest));
    }

    @GetMapping(path = "/get")
    public ResponseEntity<?> getPayment(
            @RequestParam(defaultValue = "", name = "paymentId") String paymentId
    ) throws PaymentNotFoundException {

        if (Objects.equals(paymentId, "")) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .message("payment id is empty")
                    .build();

            return ResponseEntity.status(400).body(errorResponse);
        }

        UUID id = UUID.fromString(paymentId);
        return ResponseEntity.status(200).body(paymentService.getPayment(id));
    }
}
