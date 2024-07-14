package com.praveenukkoji.userservice.exception;

public class UserCreateException extends Exception {
    public UserCreateException() {
        super("unable to create user");
    }
}
