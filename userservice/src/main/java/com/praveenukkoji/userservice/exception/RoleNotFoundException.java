package com.praveenukkoji.userservice.exception;

public class RoleNotFoundException extends Exception {
    public RoleNotFoundException() {
        super("role not found");
    }
}
