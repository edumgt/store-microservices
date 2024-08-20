package com.praveenukkoji.productservice.controller;

import com.praveenukkoji.productservice.dto.error.ValidationResponse;
import com.praveenukkoji.productservice.dto.request.category.CreateCategoryRequest;
import com.praveenukkoji.productservice.dto.request.category.UpdateCategoryRequest;
import com.praveenukkoji.productservice.exception.category.CategoryCreateException;
import com.praveenukkoji.productservice.exception.category.CategoryNotFoundException;
import com.praveenukkoji.productservice.exception.category.CategoryUpdateException;
import com.praveenukkoji.productservice.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping(path = "")
    public ResponseEntity<?> createCategory(@RequestBody @Valid CreateCategoryRequest createCategoryRequest)
            throws CategoryCreateException {
        return ResponseEntity.status(201).body(categoryService.createCategory(createCategoryRequest));
    }

    @GetMapping(path = "/all")
    public ResponseEntity<?> getAllCategory() {
        return ResponseEntity.status(200).body(categoryService.getAllCategory());
    }

    @PatchMapping(path = "")
    public ResponseEntity<?> updateCategory(
            @RequestParam(defaultValue = "", name = "categoryId") String categoryId,
            @RequestBody @Valid UpdateCategoryRequest updateCategoryRequest
    ) throws CategoryNotFoundException, CategoryUpdateException {
        if (Objects.equals(categoryId, "")) {
            Map<String, String> error = new HashMap<>();
            error.put("categoryId", "category id is empty");

            ValidationResponse response = ValidationResponse.builder()
                    .error(error)
                    .build();

            return ResponseEntity.status(400).body(response);
        }

        UUID id = UUID.fromString(categoryId);
        return ResponseEntity.status(200).body(categoryService.updateCategory(id, updateCategoryRequest));
    }

    @DeleteMapping(path = "")
    public ResponseEntity<?> deleteCategory(
            @RequestParam(defaultValue = "", name = "categoryId") String categoryId
    ) throws CategoryNotFoundException {
        if (Objects.equals(categoryId, "")) {
            Map<String, String> error = new HashMap<>();
            error.put("categoryId", "category id is empty");

            ValidationResponse response = ValidationResponse.builder()
                    .error(error)
                    .build();

            return ResponseEntity.status(400).body(response);
        }

        UUID id = UUID.fromString(categoryId);
        categoryService.deleteCategory(id);

        return ResponseEntity.status(204).body("");
    }
}

