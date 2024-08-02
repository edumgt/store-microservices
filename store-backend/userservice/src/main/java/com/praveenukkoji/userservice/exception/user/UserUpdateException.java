package com.praveenukkoji.userservice.exception.user;

public class UserUpdateException extends Exception {
    public UserUpdateException() {
        super("unable to update user");
    }
}
