package com.praveenukkoji.userservice.advice;

import com.praveenukkoji.userservice.dto.ErrorResponse;
import com.praveenukkoji.userservice.exception.CreateUserException;
import com.praveenukkoji.userservice.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CreateUserException.class)
    public ErrorResponse handleException(CreateUserException exception) {
        log.error("CreateUserException - {}", exception.getMessage());

        return ErrorResponse.builder()
                .error_type("CreateUserException")
                .error_message(exception.getMessage())
                .build();
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ErrorResponse handleException(UserNotFoundException exception) {
        log.error("UserNotFoundException - {}", exception.getMessage());

        return ErrorResponse.builder()
                .error_type("UserNotFoundException")
                .error_message(exception.getMessage())
                .build();
    }
}
