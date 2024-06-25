package com.praveenukkoji.orderservice.advice;

import com.praveenukkoji.orderservice.dto.ErrorResponse;
import com.praveenukkoji.orderservice.exception.CreateOrderException;
import com.praveenukkoji.orderservice.exception.OrderNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(OrderNotFoundException.class)
    public ErrorResponse handleOrderNotFoundException(OrderNotFoundException exception) {
        log.error("OrderNotFoundException - {}", exception.getMessage());

        return ErrorResponse.builder()
                .error_type("OrderNotFoundException")
                .error_message(exception.getMessage())
                .build();
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CreateOrderException.class)
    public ErrorResponse handleCreateOrderException(CreateOrderException exception) {
        log.error("CreateOrderException - {}", exception.getMessage());

        return ErrorResponse.builder()
                .error_type("CreateOrderException")
                .error_message(exception.getMessage())
                .build();
    }
}
