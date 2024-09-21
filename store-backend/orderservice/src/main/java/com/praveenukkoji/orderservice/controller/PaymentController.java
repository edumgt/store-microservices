package com.praveenukkoji.orderservice.controller;

import com.praveenukkoji.orderservice.dto.error.ValidationResponse;
import com.praveenukkoji.orderservice.dto.request.payment.MakePaymentRequest;
import com.praveenukkoji.orderservice.exception.order.OrderNotFoundException;
import com.praveenukkoji.orderservice.exception.payment.CreatePaymentException;
import com.praveenukkoji.orderservice.exception.payment.PaymentNotFoundException;
import com.praveenukkoji.orderservice.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    // create
    @PostMapping(path = "")
    public ResponseEntity<?> makePayment(@RequestBody @Valid MakePaymentRequest makePaymentRequest)
            throws OrderNotFoundException, CreatePaymentException {
        return ResponseEntity.status(201).body(paymentService.makePayment(makePaymentRequest));
    }

    // retrieve
    @GetMapping(path = "")
    public ResponseEntity<?> getPayment(
            @RequestParam(defaultValue = "", name = "paymentId") String paymentId
    ) throws PaymentNotFoundException {
        if (Objects.equals(paymentId, "")) {
            Map<String, String> error = new HashMap<>();
            error.put("paymentId", "payment id is empty");

            ValidationResponse response = ValidationResponse.builder()
                    .error(error)
                    .build();

            return ResponseEntity.status(400).body(response);
        }

        UUID id = UUID.fromString(paymentId);
        return ResponseEntity.status(200).body(paymentService.getPayment(id));
    }
}
