package com.praveenukkoji.userservice.exception.user;

public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super("unable to find user");
    }
}
