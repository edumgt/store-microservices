package com.praveenukkoji.userservice.advice;

import com.praveenukkoji.userservice.dto.error.ExceptionResponse;
import com.praveenukkoji.userservice.dto.error.ValidationResponse;
import com.praveenukkoji.userservice.exception.address.AddressCreateException;
import com.praveenukkoji.userservice.exception.address.AddressNotFoundException;
import com.praveenukkoji.userservice.exception.address.AddressUpdateException;
import com.praveenukkoji.userservice.exception.address.DeleteAddressException;
import com.praveenukkoji.userservice.exception.role.RoleCreateException;
import com.praveenukkoji.userservice.exception.role.RoleNotFoundException;
import com.praveenukkoji.userservice.exception.role.RoleUpdateException;
import com.praveenukkoji.userservice.exception.user.UserCreateException;
import com.praveenukkoji.userservice.exception.user.UserNotFoundException;
import com.praveenukkoji.userservice.exception.user.UserUpdateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserCreateException.class)
    public ResponseEntity<?> handleException(UserCreateException exception) {
        log.error("UserCreateException - {}", exception.getMessage());

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(400).body(exceptionResponse);
    }

    @ExceptionHandler(UserUpdateException.class)
    public ResponseEntity<?> handleException(UserUpdateException exception) {
        log.error("UserUpdateException - {}", exception.getMessage());

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(400).body(exceptionResponse);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleException(UserNotFoundException exception) {
        log.error("UserNotFoundException - {}", exception.getMessage());

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(404).body(exceptionResponse);
    }

    @ExceptionHandler(AddressCreateException.class)
    public ResponseEntity<?> handleException(AddressCreateException exception) {
        log.error("AddressCreateException - {}", exception.getMessage());

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(400).body(exceptionResponse);
    }

    @ExceptionHandler(AddressUpdateException.class)
    public ResponseEntity<?> handleException(AddressUpdateException exception) {
        log.error("AddressUpdateException - {}", exception.getMessage());

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(400).body(exceptionResponse);
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<?> handleException(AddressNotFoundException exception) {
        log.error("AddressNotFoundException - {}", exception.getMessage());

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(404).body(exceptionResponse);
    }

    @ExceptionHandler(DeleteAddressException.class)
    public ResponseEntity<?> handleException(DeleteAddressException exception) {
        log.error("DeleteAddressException - {}", exception.getMessage());

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(400).body(exceptionResponse);
    }

    @ExceptionHandler(RoleCreateException.class)
    public ResponseEntity<?> handleException(RoleCreateException exception) {
        log.error("RoleCreateException - {}", exception.getMessage());

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(400).body(exceptionResponse);
    }

    @ExceptionHandler(RoleUpdateException.class)
    public ResponseEntity<?> handleException(RoleUpdateException exception) {
        log.error("RoleUpdateException - {}", exception.getMessage());

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(400).body(exceptionResponse);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<?> handleException(RoleNotFoundException exception) {
        log.error("RoleNotFoundException - {}", exception.getMessage());

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(404).body(exceptionResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors()
                .forEach(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });

        log.error("MethodArgumentNotValidException - {}", errors);

        ValidationResponse validationResponse = ValidationResponse.builder()
                .error(errors)
                .build();

        return ResponseEntity.status(400).body(validationResponse);
    }

}
