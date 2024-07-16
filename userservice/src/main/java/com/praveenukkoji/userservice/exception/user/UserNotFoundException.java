package com.praveenukkoji.userservice.exception.user;

public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super("user not found");
    }
}
