package com.praveenukkoji.paymentservice.controller;

import com.praveenukkoji.paymentservice.dto.payment.request.MakePaymentRequest;
import com.praveenukkoji.paymentservice.exception.error.ValidationException;
import com.praveenukkoji.paymentservice.exception.payment.CreatePaymentException;
import com.praveenukkoji.paymentservice.exception.payment.PaymentNotFoundException;
import com.praveenukkoji.paymentservice.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    // create
    @PostMapping(path = "")
    public ResponseEntity<?> makePayment(@RequestBody @Valid MakePaymentRequest makePaymentRequest)
            throws CreatePaymentException, ValidationException {
        return ResponseEntity.status(201).body(paymentService.makePayment(makePaymentRequest));
    }

    // get
    @GetMapping(path = "")
    public ResponseEntity<?> getPayment(
            @RequestParam(defaultValue = "", name = "paymentId") String paymentId
    ) throws PaymentNotFoundException, ValidationException {
        return ResponseEntity.status(200).body(paymentService.getPayment(paymentId));
    }
}
