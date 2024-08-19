package com.praveenukkoji.userservice.exception.address;

public class AddressUpdateException extends Exception {
    public AddressUpdateException() {
        super("unable to update address");
    }

    public AddressUpdateException(String message) {
        super(message);
    }
}
