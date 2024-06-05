package com.praveenukkoji.orderservice.advice;

import com.praveenukkoji.orderservice.exception.OrderDoesNotExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(OrderDoesNotExistException.class)
    public Map<String, String> handleException(OrderDoesNotExistException exception) {
        Map<String, String> errorMap = new HashMap<>();

        log.error("OrderDoesNotExistException - {}", exception.getMessage());
        errorMap.put("error", "OrderDoesNotExistException - " + exception.getMessage());

        return errorMap;
    }
}
