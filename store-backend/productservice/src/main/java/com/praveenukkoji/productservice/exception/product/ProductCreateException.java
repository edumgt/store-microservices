package com.praveenukkoji.productservice.exception.product;

public class ProductCreateException extends Exception {
    public ProductCreateException() {
        super("unable to create product");
    }

    public ProductCreateException(String message) {
        super(message);
    }
}
