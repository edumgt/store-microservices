package com.praveenukkoji.orderservice.exception;

public class ProductDoesNotExist extends Exception {
    public ProductDoesNotExist(String message) {
        super(message);
    }
}
