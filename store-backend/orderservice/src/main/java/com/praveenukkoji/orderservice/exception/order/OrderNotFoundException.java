package com.praveenukkoji.orderservice.exception.order;

public class OrderNotFoundException extends Exception {
    public OrderNotFoundException(String message) {
        super(message);
    }
}
