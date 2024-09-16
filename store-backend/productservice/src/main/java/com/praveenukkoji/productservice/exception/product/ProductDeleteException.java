package com.praveenukkoji.productservice.exception.product;

public class ProductDeleteException extends Exception {
    public ProductDeleteException(String message) {
        super(message);
    }

    public ProductDeleteException() {
        super("unable to delete product");
    }
}
