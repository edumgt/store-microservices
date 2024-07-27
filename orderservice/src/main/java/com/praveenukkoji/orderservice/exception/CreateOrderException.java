package com.praveenukkoji.orderservice.exception;

public class CreateOrderException extends Exception {
    public CreateOrderException() {
        super("unable to create order");
    }
}
