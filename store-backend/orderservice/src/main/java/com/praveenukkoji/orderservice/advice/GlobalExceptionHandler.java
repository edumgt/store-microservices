package com.praveenukkoji.orderservice.advice;

import com.praveenukkoji.orderservice.dto.error.ErrorResponse;
import com.praveenukkoji.orderservice.dto.error.ExceptionResponse;
import com.praveenukkoji.orderservice.exception.order.CreateOrderException;
import com.praveenukkoji.orderservice.exception.order.OrderNotFoundException;
import com.praveenukkoji.orderservice.exception.payment.PaymentNotFoundException;
import com.praveenukkoji.orderservice.exception.product.ProductDoesNotExist;
import com.praveenukkoji.orderservice.exception.product.QuantityNotAvailable;
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

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<?> handleException(OrderNotFoundException exception) {
        log.error("OrderNotFoundException - {}", exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(404).body(errorResponse);
    }

    @ExceptionHandler(CreateOrderException.class)
    public ResponseEntity<?> handleException(CreateOrderException exception) {
        log.error("CreateOrderException - {}", exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(400).body(errorResponse);
    }

    @ExceptionHandler(ProductDoesNotExist.class)
    public ResponseEntity<?> handleException(ProductDoesNotExist exception) {
        log.error("ProductDoesNotExist - {}", exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(404).body(errorResponse);
    }

    @ExceptionHandler(QuantityNotAvailable.class)
    public ResponseEntity<?> handleException(QuantityNotAvailable exception) {
        log.error("QuantityNotAvailable - {}", exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(400).body(errorResponse);
    }

    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<?> handleException(PaymentNotFoundException exception) {
        log.error("PaymentNotFoundException - {}", exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(404).body(errorResponse);
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
