package com.praveenukkoji.userservice.advice;

import com.praveenukkoji.userservice.dto.error.ErrorResponse;
import com.praveenukkoji.userservice.exception.address.AddressCreateException;
import com.praveenukkoji.userservice.exception.address.AddressDeleteException;
import com.praveenukkoji.userservice.exception.address.AddressNotFoundException;
import com.praveenukkoji.userservice.exception.address.AddressUpdateException;
import com.praveenukkoji.userservice.exception.error.ValidationException;
import com.praveenukkoji.userservice.exception.role.RoleCreateException;
import com.praveenukkoji.userservice.exception.role.RoleDeleteException;
import com.praveenukkoji.userservice.exception.role.RoleNotFoundException;
import com.praveenukkoji.userservice.exception.role.RoleUpdateException;
import com.praveenukkoji.userservice.exception.user.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // user
    @ExceptionHandler(UserCreateException.class)
    public ResponseEntity<?> handleException(UserCreateException exception) {
        log.error("UserCreateException - {}", exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Bad Request")
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(400).body(errorResponse);
    }

    @ExceptionHandler(UserUpdateException.class)
    public ResponseEntity<?> handleException(UserUpdateException exception) {
        log.error("UserUpdateException - {}", exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Bad Request")
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(400).body(errorResponse);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleException(UserNotFoundException exception) {
        log.error("UserNotFoundException - {}", exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error("Not Found")
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(404).body(errorResponse);
    }

    @ExceptionHandler(UserDeleteException.class)
    public ResponseEntity<?> handleException(UserDeleteException exception) {
        log.error("UserDeleteException - {}", exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Bad Request")
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(400).body(errorResponse);
    }

    @ExceptionHandler(PasswordEncryptionException.class)
    public ResponseEntity<?> handleException(PasswordEncryptionException exception) {
        log.error("PasswordEncryptionException - {}", exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Bad Request")
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(400).body(errorResponse);
    }

    @ExceptionHandler(PasswordDecryptionException.class)
    public ResponseEntity<?> handleException(PasswordDecryptionException exception) {
        log.error("PasswordDecryptionException - {}", exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Bad Request")
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(400).body(errorResponse);
    }

    // address
    @ExceptionHandler(AddressCreateException.class)
    public ResponseEntity<?> handleException(AddressCreateException exception) {
        log.error("AddressCreateException - {}", exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Bad Request")
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(400).body(errorResponse);
    }

    @ExceptionHandler(AddressUpdateException.class)
    public ResponseEntity<?> handleException(AddressUpdateException exception) {
        log.error("AddressUpdateException - {}", exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Bad Request")
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(400).body(errorResponse);
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<?> handleException(AddressNotFoundException exception) {
        log.error("AddressNotFoundException - {}", exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error("Not Found")
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(404).body(errorResponse);
    }

    @ExceptionHandler(AddressDeleteException.class)
    public ResponseEntity<?> handleException(AddressDeleteException exception) {
        log.error("AddressDeleteException - {}", exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Bad Request")
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(400).body(errorResponse);
    }

    // role
    @ExceptionHandler(RoleCreateException.class)
    public ResponseEntity<?> handleException(RoleCreateException exception) {
        log.error("RoleCreateException - {}", exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Bad Request")
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(400).body(errorResponse);
    }

    @ExceptionHandler(RoleUpdateException.class)
    public ResponseEntity<?> handleException(RoleUpdateException exception) {
        log.error("RoleUpdateException - {}", exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Bad Request")
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(400).body(errorResponse);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<?> handleException(RoleNotFoundException exception) {
        log.error("RoleNotFoundException - {}", exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error("Not Found")
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(404).body(errorResponse);
    }

    @ExceptionHandler(RoleDeleteException.class)
    public ResponseEntity<?> handleException(RoleDeleteException exception) {
        log.error("RoleDeleteException - {}", exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Bad Request")
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(400).body(errorResponse);
    }

    // argument validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getAllErrors()
                .forEach(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = Optional.ofNullable(error.getDefaultMessage())
                            .orElse("validation error");
                    errors.put(fieldName, errorMessage);
                });

        log.error("MethodArgumentNotValidException - {}", errors);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Bad Request")
                .message("validation error")
                .errors(errors)
                .build();

        return ResponseEntity.status(400).body(errorResponse);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleException(ValidationException exception) {
        log.error("ValidationException - {} : {}", exception.getMessage(), exception.getCause().getMessage());

        Map<String, String> errors = new HashMap<>();
        errors.put(exception.getMessage(), exception.getCause().getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Bad Request")
                .message("validation error")
                .errors(errors)
                .build();

        return ResponseEntity.status(400).body(errorResponse);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<?> handleException(HandlerMethodValidationException exception) {
        Map<String, String> errors = new HashMap<>();

        exception.getAllErrors()
                .forEach(error -> {
                    if (error instanceof FieldError) {
                        String fieldName = ((FieldError) error).getField();
                        String errorMessage = error.getDefaultMessage();
                        errors.put(fieldName, errorMessage);
                    }
                });

        log.error("HandlerMethodValidationException - {}", errors);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Bad Request")
                .message("validation error")
                .errors(errors)
                .build();

        return ResponseEntity.status(400).body(errorResponse);
    }

    // sql exception
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleException(DataIntegrityViolationException exception) {
        log.error("DataIntegrityViolationException - {}", "" + exception.getMostSpecificCause());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Bad Request")
                .message(String.valueOf(exception.getMostSpecificCause()))
                .build();

        return ResponseEntity.status(400).body(errorResponse);
    }
}
