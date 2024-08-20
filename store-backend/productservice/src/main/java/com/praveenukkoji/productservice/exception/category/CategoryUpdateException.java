package com.praveenukkoji.productservice.exception.category;

public class CategoryUpdateException extends Exception {
    public CategoryUpdateException() {
        super("unable to update category");
    }

    public CategoryUpdateException(String message) {
        super(message);
    }
}
