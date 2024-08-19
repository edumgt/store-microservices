package com.praveenukkoji.userservice.exception.address;

public class DeleteAddressException extends Exception {
    public DeleteAddressException(String message) {
        super(message);
    }

    public DeleteAddressException() {
        super("cannot delete address");
    }
}
