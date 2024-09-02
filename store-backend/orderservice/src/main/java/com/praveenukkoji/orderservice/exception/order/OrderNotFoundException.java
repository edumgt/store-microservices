package com.praveenukkoji.orderservice.exception.order;

public class OrderNotFoundException extends Exception {
    public OrderNotFoundException() {
        super("order not found");
    }

    public OrderNotFoundException(String message) {
        super(message);
    }
}
