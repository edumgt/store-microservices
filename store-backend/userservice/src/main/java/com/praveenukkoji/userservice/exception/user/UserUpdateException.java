package com.praveenukkoji.userservice.exception.user;

public class UserUpdateException extends Exception {
    public UserUpdateException() {
        super("unable to update user");
    }

    public UserUpdateException(String message) {
        super(message);
    }
}
