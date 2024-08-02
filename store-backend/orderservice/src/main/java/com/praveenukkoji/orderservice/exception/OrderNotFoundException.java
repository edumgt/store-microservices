package com.praveenukkoji.orderservice.exception;

public class OrderNotFoundException extends Exception {
    public OrderNotFoundException() {
        super("order not found");
    }
}
