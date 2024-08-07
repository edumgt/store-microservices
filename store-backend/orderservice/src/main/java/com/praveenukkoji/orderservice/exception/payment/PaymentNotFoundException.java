package com.praveenukkoji.orderservice.exception.payment;

public class PaymentNotFoundException extends Exception {
    public PaymentNotFoundException() {
        super("unable to find payment");
    }
}
