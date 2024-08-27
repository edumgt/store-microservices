package com.praveenukkoji.productservice.exception.product;

public class ProductNotFoundException extends Exception {
    public ProductNotFoundException() {
        super("product not found");
    }

    public ProductNotFoundException(String message) {
        super(message);
    }
}