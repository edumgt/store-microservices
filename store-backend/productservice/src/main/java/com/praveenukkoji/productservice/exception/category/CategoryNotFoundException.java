package com.praveenukkoji.productservice.exception.category;

public class CategoryNotFoundException extends Exception {
    public CategoryNotFoundException() {
        super("unable to find category");
    }

    public CategoryNotFoundException(String message) {
        super(message);
    }
}
