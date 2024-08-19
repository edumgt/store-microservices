package com.praveenukkoji.userservice.exception.user;

public class UserCreateException extends Exception {
    public UserCreateException() {
        super("unable to create user");
    }

    public UserCreateException(String message) {
        super(message);
    }
}
