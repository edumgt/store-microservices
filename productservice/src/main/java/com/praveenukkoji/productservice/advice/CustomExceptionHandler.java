package com.praveenukkoji.productservice.advice;

import com.praveenukkoji.productservice.dto.ErrorResponse;
import com.praveenukkoji.productservice.exception.CreateProductException;
import com.praveenukkoji.productservice.exception.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CreateProductException.class)
    public ErrorResponse handleCreateProductException(CreateProductException exception) {
        log.error("CreateProductException - {}", exception.getMessage());

        return ErrorResponse.builder()
                .error_type("CreateProductException")
                .error_message(exception.getMessage())
                .build();
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    public ErrorResponse handleProductNotFoundException(ProductNotFoundException exception) {
        log.error("ProductNotFoundException - {}", exception.getMessage());

        return ErrorResponse.builder()
                .error_type("ProductNotFoundException")
                .error_message(exception.getMessage())
                .build();
    }
}
