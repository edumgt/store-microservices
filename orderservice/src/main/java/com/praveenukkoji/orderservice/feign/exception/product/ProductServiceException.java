package com.praveenukkoji.orderservice.feign.exception.product;

public class ProductServiceException extends Exception {
    public ProductServiceException(String message) {
        super(message);
    }
}
