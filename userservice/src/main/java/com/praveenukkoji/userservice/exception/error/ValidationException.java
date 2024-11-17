package com.praveenukkoji.userservice.exception.error;

public class ValidationException extends Exception{
    public ValidationException(String field, String message) {super(field, new Throwable(message));}
}
