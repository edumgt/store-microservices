package com.praveenukkoji.productservice.advice;

import com.praveenukkoji.productservice.dto.Response;
import com.praveenukkoji.productservice.exception.category.CategoryNotFoundException;
import com.praveenukkoji.productservice.exception.category.CategoryUpdateException;
import com.praveenukkoji.productservice.exception.category.CreateCategoryException;
import com.praveenukkoji.productservice.exception.product.CreateProductException;
import com.praveenukkoji.productservice.exception.product.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CreateProductException.class)
    public ResponseEntity<?> handleException(CreateProductException exception) {
        log.error("CreateProductException - {}", exception.getMessage());

        Response response = Response.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(400).body(response);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<?> handleException(ProductNotFoundException exception) {
        log.error("ProductNotFoundException - {}", exception.getMessage());

        Response response = Response.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(CreateCategoryException.class)
    public ResponseEntity<?> handleException(CreateCategoryException exception) {
        log.error("CreateCategoryException - {}", exception.getMessage());

        Response response = Response.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(400).body(response);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<?> handleException(CategoryNotFoundException exception) {
        log.error("CategoryNotFoundException - {}", exception.getMessage());

        Response response = Response.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(CategoryUpdateException.class)
    public ResponseEntity<?> handleException(CategoryUpdateException exception) {
        log.error("CategoryUpdateException - {}", exception.getMessage());

        Response response = Response.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(400).body(response);
    }
}
