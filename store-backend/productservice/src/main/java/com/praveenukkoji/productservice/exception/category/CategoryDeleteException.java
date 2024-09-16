package com.praveenukkoji.productservice.exception.category;

public class CategoryDeleteException extends Exception {
    public CategoryDeleteException() {
        super("unable to delete category");
    }

    public CategoryDeleteException(String message) {
        super(message);
    }
}
