package com.praveenukkoji.userservice.exception.address;

public class AddressCreateException extends Exception {
    public AddressCreateException() {
        super("unable to create address");
    }

    public AddressCreateException(String message) {
        super(message);
    }
}
