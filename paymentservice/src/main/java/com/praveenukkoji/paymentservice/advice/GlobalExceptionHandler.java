package com.praveenukkoji.paymentservice.advice;

import com.praveenukkoji.paymentservice.dto.error.ErrorResponse;
import com.praveenukkoji.paymentservice.exception.error.ValidationException;
import com.praveenukkoji.paymentservice.exception.payment.CreatePaymentException;
import com.praveenukkoji.paymentservice.exception.payment.PaymentNotFoundException;
import com.praveenukkoji.paymentservice.exception.payment.PaymentStatusUpdateException;
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
    
    // payment
    @ExceptionHandler(CreatePaymentException.class)
    public ResponseEntity<?> handleException(CreatePaymentException exception) {
        log.error("CreatePaymentException - {}", exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Bad Request")
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(400).body(errorResponse);
    }

    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<?> handleException(PaymentNotFoundException exception) {
        log.error("PaymentNotFoundException - {}", exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error("Not Found")
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(404).body(errorResponse);
    }

    @ExceptionHandler(PaymentStatusUpdateException.class)
    public ResponseEntity<?> handleException(PaymentStatusUpdateException exception) {
        log.error("PaymentStatusUpdateException - {}", exception.getMessage());

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
