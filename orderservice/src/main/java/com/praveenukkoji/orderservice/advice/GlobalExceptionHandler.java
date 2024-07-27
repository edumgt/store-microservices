package com.praveenukkoji.orderservice.advice;

import com.praveenukkoji.orderservice.dto.Response;
import com.praveenukkoji.orderservice.exception.CreateOrderException;
import com.praveenukkoji.orderservice.exception.OrderNotFoundException;
import com.praveenukkoji.orderservice.exception.ProductDoesNotExist;
import com.praveenukkoji.orderservice.exception.QuantityNotAvailable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<?> handleException(OrderNotFoundException exception) {
        log.error("OrderNotFoundException - {}", exception.getMessage());

        Response response = Response.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(CreateOrderException.class)
    public ResponseEntity<?> handleException(CreateOrderException exception) {
        log.error("CreateOrderException - {}", exception.getMessage());

        Response response = Response.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(400).body(response);
    }

    @ExceptionHandler(ProductDoesNotExist.class)
    public ResponseEntity<?> handleException(ProductDoesNotExist exception) {
        log.error("ProductDoesNotExist - {}", exception.getMessage());

        Response response = Response.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(QuantityNotAvailable.class)
    public ResponseEntity<?> handleException(QuantityNotAvailable exception) {
        log.error("QuantityNotAvailable - {}", exception.getMessage());

        Response response = Response.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(400).body(response);
    }
}
