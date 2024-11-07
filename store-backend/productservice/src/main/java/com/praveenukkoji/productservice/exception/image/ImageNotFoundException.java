package com.praveenukkoji.productservice.exception.image;

public class ImageNotFoundException extends Exception {
    public ImageNotFoundException(String message) {
        super(message);
    }

    public ImageNotFoundException() {
        super("image not found");
    }
}
