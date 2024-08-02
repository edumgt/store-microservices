package com.praveenukkoji.userservice.exception.address;

public class AddressNotFoundException extends Exception {
    public AddressNotFoundException() {
        super("address not found");
    }
}
