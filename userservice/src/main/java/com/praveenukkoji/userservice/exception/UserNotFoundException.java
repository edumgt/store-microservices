package com.praveenukkoji.userservice.exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super("user not found");
    }
}
