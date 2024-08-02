package com.praveenukkoji.userservice.exception.role;

public class RoleNotFoundException extends Exception {
    public RoleNotFoundException() {
        super("role not found");
    }
}
