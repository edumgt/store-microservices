package com.praveenukkoji.productservice.exception.category;

public class CategoryCreateException extends Exception {
    public CategoryCreateException() {
        super("unable to create category");
    }

    public CategoryCreateException(String message) {
        super(message);
    }
}
