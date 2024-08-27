package com.praveenukkoji.productservice.exception.category;

public class CategoryNotFoundException extends Exception {
    public CategoryNotFoundException() {
        super("category not found");
    }

    public CategoryNotFoundException(String message) {
        super(message);
    }
}
