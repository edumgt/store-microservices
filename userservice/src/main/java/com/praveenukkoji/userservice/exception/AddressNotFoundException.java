package com.praveenukkoji.userservice.exception;

public class AddressNotFoundException extends Exception {
    public AddressNotFoundException() {
        super("address not found");
    }
}
