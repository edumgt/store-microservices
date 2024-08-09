package com.praveenukkoji.orderservice.exception.payment;

public class CreatePaymentException extends Exception {
    public CreatePaymentException() {
        super("unable to create payment");
    }
}
