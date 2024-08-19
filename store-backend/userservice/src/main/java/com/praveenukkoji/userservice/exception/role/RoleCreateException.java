package com.praveenukkoji.userservice.exception.role;

public class RoleCreateException extends Exception {
    public RoleCreateException() {
        super("unable to create role");
    }

    public RoleCreateException(String message) {
        super(message);
    }
}
