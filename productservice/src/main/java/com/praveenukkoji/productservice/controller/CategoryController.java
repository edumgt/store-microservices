package com.praveenukkoji.productservice.controller;

import com.praveenukkoji.productservice.dto.error.ErrorResponse;
import com.praveenukkoji.productservice.dto.category.request.CreateCategoryRequest;
import com.praveenukkoji.productservice.dto.category.request.UpdateCategoryRequest;
import com.praveenukkoji.productservice.exception.category.CategoryCreateException;
import com.praveenukkoji.productservice.exception.category.CategoryDeleteException;
import com.praveenukkoji.productservice.exception.category.CategoryNotFoundException;
import com.praveenukkoji.productservice.exception.category.CategoryUpdateException;
import com.praveenukkoji.productservice.exception.error.ValidationException;
import com.praveenukkoji.productservice.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    // create
    @PostMapping(path = "")
    public ResponseEntity<?> createCategory(@RequestBody @Valid CreateCategoryRequest createCategoryRequest)
            throws CategoryCreateException, ValidationException {
        return ResponseEntity.status(201).body(categoryService.createCategory(createCategoryRequest));
    }

    // get
    @GetMapping(path = "")
    public ResponseEntity<?> getCategory(@RequestParam(defaultValue = "", name = "categoryId") String categoryId)
            throws CategoryNotFoundException, ValidationException {
        return ResponseEntity.status(200).body(categoryService.getCategory(categoryId));
    }

    // update
    @PatchMapping(path = "")
    public ResponseEntity<?> updateCategory(@RequestBody @Valid UpdateCategoryRequest updateCategoryRequest)
            throws CategoryNotFoundException, CategoryUpdateException, ValidationException {
        categoryService.updateCategory(updateCategoryRequest);
        return ResponseEntity.status(204).body("");
    }

    // delete
    @DeleteMapping(path = "")
    public ResponseEntity<?> deleteCategory(@RequestParam(defaultValue = "", name = "categoryId") String categoryId)
            throws CategoryNotFoundException, CategoryDeleteException, ValidationException {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.status(204).body("");
    }

    // get all
    @GetMapping(path = "/all")
    public ResponseEntity<?> getAllCategory() {
        return ResponseEntity.status(200).body(categoryService.getAllCategory());
    }
}

