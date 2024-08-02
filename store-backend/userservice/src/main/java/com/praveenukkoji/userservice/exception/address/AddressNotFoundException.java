package com.praveenukkoji.userservice.exception.address;

public class AddressNotFoundException extends Exception {
    public AddressNotFoundException() {
        super("unable to find address");
    }
}
