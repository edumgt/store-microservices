package com.praveenukkoji.orderservice.exception.product;

public class ProductNotInStock extends Exception {
    public ProductNotInStock(String message) {
        super(message);
    }
}
