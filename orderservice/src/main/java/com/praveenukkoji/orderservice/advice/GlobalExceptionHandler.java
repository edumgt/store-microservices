package com.praveenukkoji.orderservice.advice;

import com.praveenukkoji.orderservice.dto.error.ErrorResponse;
import com.praveenukkoji.orderservice.exception.error.ValidationException;
import com.praveenukkoji.orderservice.exception.order.CreateOrderException;
import com.praveenukkoji.orderservice.exception.order.OrderNotFoundException;
import com.praveenukkoji.orderservice.exception.order.OrderStatusUpdateException;
import com.praveenukkoji.orderservice.exception.order.ProductNotFoundException;
import com.praveenukkoji.orderservice.feign.exception.product.ProductServiceException;
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

    // order
    @ExceptionHandler(CreateOrderException.class)
    public ResponseEntity<?> handleException(CreateOrderException exception) {
        log.error("CreateOrderException - {}", exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Bad Request")
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(400).body(errorResponse);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<?> handleException(OrderNotFoundException exception) {
        log.error("OrderNotFoundException - {}", exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error("Not Found")
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(404).body(errorResponse);
    }

    @ExceptionHandler(OrderStatusUpdateException.class)
    public ResponseEntity<?> handleException(OrderStatusUpdateException exception) {
        log.error("OrderStatusUpdateException - {}", exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Bad Request")
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(400).body(errorResponse);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<?> handleException(ProductNotFoundException exception) {
        log.error("ProductNotFoundException - {}", exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error("Not Found")
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(404).body(errorResponse);
    }

    // product-service
    @ExceptionHandler(ProductServiceException.class)
    public ResponseEntity<?> handleException(ProductServiceException exception) {
        log.error("ProductServiceException - {}", exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.SERVICE_UNAVAILABLE.value())
                .error("Service Unavailable")
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(503).body(errorResponse);
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
