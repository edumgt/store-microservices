package com.praveenukkoji.orderservice.exception;

public class OrderDoesNotExistException extends Exception {
    public OrderDoesNotExistException(String message) {
        super(message);
    }
}
