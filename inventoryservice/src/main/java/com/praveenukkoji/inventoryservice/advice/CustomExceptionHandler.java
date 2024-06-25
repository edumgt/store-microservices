package com.praveenukkoji.inventoryservice.advice;

import com.praveenukkoji.inventoryservice.dto.ErrorResponse;
import com.praveenukkoji.inventoryservice.exception.CreateInventoryException;
import com.praveenukkoji.inventoryservice.exception.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CreateInventoryException.class)
    public ErrorResponse handleCreateInventoryException(CreateInventoryException exception) {
        log.error("CreateInventoryException - {}", exception.getMessage());

        return ErrorResponse.builder()
                .error_type("CreateInventoryException")
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
