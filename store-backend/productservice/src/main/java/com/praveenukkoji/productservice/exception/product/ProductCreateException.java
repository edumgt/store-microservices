package com.praveenukkoji.productservice.exception.product;

public class CreateProductException extends Exception {
    public CreateProductException() {
        super("unable to create product");
    }
}
