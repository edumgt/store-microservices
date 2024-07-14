package com.praveenukkoji.userservice.exception;

public class UserUpdateException extends Exception {
    public UserUpdateException() {
        super("unable to update user");
    }
}
