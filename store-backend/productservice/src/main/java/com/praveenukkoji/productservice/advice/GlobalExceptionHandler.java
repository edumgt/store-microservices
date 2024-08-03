package com.praveenukkoji.productservice.advice;

import com.praveenukkoji.productservice.dto.error.ErrorResponse;
import com.praveenukkoji.productservice.dto.error.ExceptionResponse;
import com.praveenukkoji.productservice.exception.category.CategoryNotFoundException;
import com.praveenukkoji.productservice.exception.category.CategoryUpdateException;
import com.praveenukkoji.productservice.exception.category.CreateCategoryException;
import com.praveenukkoji.productservice.exception.product.CreateProductException;
import com.praveenukkoji.productservice.exception.product.ProductNotFoundException;
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

    @ExceptionHandler(CreateProductException.class)
    public ResponseEntity<?> handleException(CreateProductException exception) {
        log.error("CreateProductException - {}", exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(400).body(errorResponse);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<?> handleException(ProductNotFoundException exception) {
        log.error("ProductNotFoundException - {}", exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(404).body(errorResponse);
    }

    @ExceptionHandler(CreateCategoryException.class)
    public ResponseEntity<?> handleException(CreateCategoryException exception) {
        log.error("CreateCategoryException - {}", exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(400).body(errorResponse);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<?> handleException(CategoryNotFoundException exception) {
        log.error("CategoryNotFoundException - {}", exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(404).body(errorResponse);
    }

    @ExceptionHandler(CategoryUpdateException.class)
    public ResponseEntity<?> handleException(CategoryUpdateException exception) {
        log.error("CategoryUpdateException - {}", exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(400).body(errorResponse);
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
