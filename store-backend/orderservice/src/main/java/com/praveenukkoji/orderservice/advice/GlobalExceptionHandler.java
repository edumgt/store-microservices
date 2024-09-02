package com.praveenukkoji.orderservice.advice;

import com.praveenukkoji.orderservice.dto.error.ExceptionResponse;
import com.praveenukkoji.orderservice.dto.error.ValidationResponse;
import com.praveenukkoji.orderservice.exception.order.CreateOrderException;
import com.praveenukkoji.orderservice.exception.order.OrderNotFoundException;
import com.praveenukkoji.orderservice.exception.payment.CreatePaymentException;
import com.praveenukkoji.orderservice.exception.payment.PaymentNotFoundException;
import com.praveenukkoji.orderservice.exception.product.ProductNotInStock;
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

    @ExceptionHandler(ProductNotInStock.class)
    public ResponseEntity<?> handleException(ProductNotInStock exception) {
        log.error("ProductNotInStock - {}", exception.getMessage());

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(200).body(exceptionResponse);
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
