package com.praveenukkoji.userservice.exception.role;

public class RoleUpdateException extends Exception {
    public RoleUpdateException() {
        super("unable to update role");
    }

    public RoleUpdateException(String message) {
        super(message);
    }
}
