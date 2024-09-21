package com.praveenukkoji.orderservice.exception.payment;

public class PaymentNotFoundException extends Exception {
    public PaymentNotFoundException(String message) {
        super(message);
    }
}
