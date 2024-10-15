package com.praveenukkoji.orderservice.advice;

import com.praveenukkoji.orderservice.dto.error.ExceptionResponse;
import com.praveenukkoji.orderservice.dto.error.ValidationResponse;
import com.praveenukkoji.orderservice.exception.order.CreateOrderException;
import com.praveenukkoji.orderservice.exception.order.OrderNotFoundException;
import com.praveenukkoji.orderservice.exception.order.OrderStatusUpdateException;
import com.praveenukkoji.orderservice.exception.payment.CreatePaymentException;
import com.praveenukkoji.orderservice.exception.payment.PaymentNotFoundException;
import com.praveenukkoji.orderservice.exception.payment.PaymentStatusUpdateException;
import com.praveenukkoji.orderservice.external.product.exception.ProductServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
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

    // order
    @ExceptionHandler(CreateOrderException.class)
    public ResponseEntity<?> handleException(CreateOrderException exception) {
        log.error("CreateOrderException - {}", exception.getMessage());

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(400).body(exceptionResponse);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<?> handleException(OrderNotFoundException exception) {
        log.error("OrderNotFoundException - {}", exception.getMessage());

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(404).body(exceptionResponse);
    }

    @ExceptionHandler(OrderStatusUpdateException.class)
    public ResponseEntity<?> handleException(OrderStatusUpdateException exception) {
        log.error("OrderStatusUpdateException - {}", exception.getMessage());

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(400).body(exceptionResponse);
    }

    // payment
    @ExceptionHandler(CreatePaymentException.class)
    public ResponseEntity<?> handleException(CreatePaymentException exception) {
        log.error("CreatePaymentException - {}", exception.getMessage());

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(400).body(exceptionResponse);
    }

    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<?> handleException(PaymentNotFoundException exception) {
        log.error("PaymentNotFoundException - {}", exception.getMessage());

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(404).body(exceptionResponse);
    }

    @ExceptionHandler(PaymentStatusUpdateException.class)
    public ResponseEntity<?> handleException(PaymentStatusUpdateException exception) {
        log.error("PaymentStatusUpdateException - {}", exception.getMessage());

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(400).body(exceptionResponse);
    }

    // product-service
    @ExceptionHandler(ProductServiceException.class)
    public ResponseEntity<?> handleException(ProductServiceException exception) {
        log.error("ProductServiceException - {}", exception.getMessage());

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(400).body(exceptionResponse);
    }

    // argument validation
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

    // sql exception
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleException(DataIntegrityViolationException exception) {
        log.error("DataIntegrityViolationException - {}", "" + exception.getMostSpecificCause());

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message("" + exception.getMostSpecificCause())
                .build();

        return ResponseEntity.status(400).body(exceptionResponse);
    }
}
