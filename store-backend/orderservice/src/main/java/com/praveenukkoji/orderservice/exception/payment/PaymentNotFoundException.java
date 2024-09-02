package com.praveenukkoji.orderservice.exception.payment;

public class PaymentNotFoundException extends Exception {
    public PaymentNotFoundException() {
        super("payment not found");
    }

    public PaymentNotFoundException(String message) {
        super(message);
    }
}
