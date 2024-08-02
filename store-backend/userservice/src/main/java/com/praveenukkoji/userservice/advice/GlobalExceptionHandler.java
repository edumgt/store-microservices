package com.praveenukkoji.userservice.advice;

import com.praveenukkoji.userservice.dto.error.ErrorResponse;
import com.praveenukkoji.userservice.dto.error.ExceptionResponse;
import com.praveenukkoji.userservice.exception.address.AddressCreateException;
import com.praveenukkoji.userservice.exception.address.AddressNotFoundException;
import com.praveenukkoji.userservice.exception.address.AddressUpdateException;
import com.praveenukkoji.userservice.exception.role.RoleCreateException;
import com.praveenukkoji.userservice.exception.role.RoleNotFoundException;
import com.praveenukkoji.userservice.exception.role.RoleUpdateException;
import com.praveenukkoji.userservice.exception.user.UserCreateException;
import com.praveenukkoji.userservice.exception.user.UserNotFoundException;
import com.praveenukkoji.userservice.exception.user.UserUpdateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserCreateException.class)
    public ResponseEntity<?> handleException(UserCreateException exception) {
        log.error("UserCreateException - {}", exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(400).body(errorResponse);
    }

    @ExceptionHandler(UserUpdateException.class)
    public ResponseEntity<?> handleException(UserUpdateException exception) {
        log.error("UserUpdateException - {}", exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(400).body(errorResponse);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleException(UserNotFoundException exception) {
        log.error("UserNotFoundException - {}", exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(404).body(errorResponse);
    }

    @ExceptionHandler(AddressCreateException.class)
    public ResponseEntity<?> handleException(AddressCreateException exception) {
        log.error("AddressCreateException - {}", exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(400).body(errorResponse);
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<?> handleException(AddressNotFoundException exception) {
        log.error("AddressNotFoundException - {}", exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(404).body(errorResponse);
    }

    @ExceptionHandler(AddressUpdateException.class)
    public ResponseEntity<?> handleException(AddressUpdateException exception) {
        log.error("AddressUpdateException - {}", exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(400).body(errorResponse);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<?> handleException(RoleNotFoundException exception) {
        log.error("RoleNotFoundException - {}", exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(404).body(errorResponse);
    }

    @ExceptionHandler(RoleCreateException.class)
    public ResponseEntity<?> handleException(RoleCreateException exception) {
        log.error("RoleCreateException - {}", exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(400).body(errorResponse);
    }

    @ExceptionHandler(RoleUpdateException.class)
    public ResponseEntity<?> handleException(RoleUpdateException exception) {
        log.error("RoleUpdateException - {}", exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(400).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleException(MethodArgumentNotValidException exception) {
        Set<String> errors = new HashSet<>();
        exception.getBindingResult().getAllErrors()
                .forEach(error -> {
                    //var fieldName = ((FieldError) error).getField();
                    var errorMessage = error.getDefaultMessage();
                    errors.add(errorMessage);
                });

        log.error("MethodArgumentNotValidException - {}", errors);

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .validationErrors(errors)
                .build();

        return ResponseEntity.status(400).body(exceptionResponse);
    }
}
