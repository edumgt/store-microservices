package com.praveenukkoji.orderservice.exception.product;

public class ProductNotFound extends Exception {
    public ProductNotFound() {
        super("product not found");
    }

    public ProductNotFound(String message) {
        super(message);
    }
}
