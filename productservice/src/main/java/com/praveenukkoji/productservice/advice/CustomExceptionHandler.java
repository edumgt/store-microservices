package com.praveenukkoji.productservice.advice;

import com.praveenukkoji.productservice.exception.product.ProductNotFoundException;
import com.praveenukkoji.productservice.exception.product.UnableToCreateProductException;
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
    @ExceptionHandler(UnableToCreateProductException.class)
    public Map<String, String> handleException(UnableToCreateProductException exception) {
        Map<String, String> errorMap = new HashMap<>();

        log.error("UnableToCreateProductException - {}", exception.getMessage());
        errorMap.put("error", "UnableToCreateProductException - " + exception.getMessage());

        return errorMap;
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    public Map<String, String> handleException(ProductNotFoundException exception) {
        Map<String, String> errorMap = new HashMap<>();

        log.error("ProductNotFoundException - {}", exception.getMessage());
        errorMap.put("error", "ProductNotFoundException - " + exception.getMessage());

        return errorMap;
    }
}
