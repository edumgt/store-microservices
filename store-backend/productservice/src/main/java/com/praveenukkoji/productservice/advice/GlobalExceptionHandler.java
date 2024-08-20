package com.praveenukkoji.productservice.advice;

import com.praveenukkoji.productservice.dto.error.ExceptionResponse;
import com.praveenukkoji.productservice.dto.error.ValidationResponse;
import com.praveenukkoji.productservice.exception.category.CategoryCreateException;
import com.praveenukkoji.productservice.exception.category.CategoryNotFoundException;
import com.praveenukkoji.productservice.exception.category.CategoryUpdateException;
import com.praveenukkoji.productservice.exception.product.ProductCreateException;
import com.praveenukkoji.productservice.exception.product.ProductNotFoundException;
import com.praveenukkoji.productservice.exception.product.ReduceQuantityException;
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

    @ExceptionHandler(ProductCreateException.class)
    public ResponseEntity<?> handleException(ProductCreateException exception) {
        log.error("ProductCreateException - {}", exception.getMessage());

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(400).body(exceptionResponse);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<?> handleException(ProductNotFoundException exception) {
        log.error("ProductNotFoundException - {}", exception.getMessage());

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(404).body(exceptionResponse);
    }

    @ExceptionHandler(ReduceQuantityException.class)
    public ResponseEntity<?> handleException(ReduceQuantityException exception) {
        log.error("ReduceQuantityException - {}", exception.getMessage());

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(400).body(exceptionResponse);
    }

    @ExceptionHandler(CategoryCreateException.class)
    public ResponseEntity<?> handleException(CategoryCreateException exception) {
        log.error("CategoryCreateException - {}", exception.getMessage());

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(400).body(exceptionResponse);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<?> handleException(CategoryNotFoundException exception) {
        log.error("CategoryNotFoundException - {}", exception.getMessage());

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(404).body(exceptionResponse);
    }

    @ExceptionHandler(CategoryUpdateException.class)
    public ResponseEntity<?> handleException(CategoryUpdateException exception) {
        log.error("CategoryUpdateException - {}", exception.getMessage());

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(400).body(exceptionResponse);
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
