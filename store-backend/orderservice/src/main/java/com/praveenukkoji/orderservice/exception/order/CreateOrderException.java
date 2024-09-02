package com.praveenukkoji.orderservice.exception.order;

public class CreateOrderException extends Exception {
    public CreateOrderException() {
        super("unable to create order");
    }

    public CreateOrderException(String message) {
        super(message);
    }
}
