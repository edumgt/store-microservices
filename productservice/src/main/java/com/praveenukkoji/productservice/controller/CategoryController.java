package com.praveenukkoji.productservice.controller;

import com.praveenukkoji.productservice.dto.Response;
import com.praveenukkoji.productservice.dto.request.category.CreateCategoryRequest;
import com.praveenukkoji.productservice.dto.request.category.UpdateCategoryRequest;
import com.praveenukkoji.productservice.exception.category.CategoryNotFoundException;
import com.praveenukkoji.productservice.exception.category.CategoryUpdateException;
import com.praveenukkoji.productservice.exception.category.CreateCategoryException;
import com.praveenukkoji.productservice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping(path = "/create")
    public ResponseEntity<?> createCategory(@RequestBody CreateCategoryRequest createCategoryRequest)
            throws CreateCategoryException {
        return ResponseEntity.status(201).body(categoryService.createCategory(createCategoryRequest));
    }

    @GetMapping(path = "/get/all")
    public ResponseEntity<?> getAllCategory() {
        return ResponseEntity.status(200).body(categoryService.getAllCategory());
    }

    @PatchMapping(path = "/update")
    public ResponseEntity<?> updateCategory(
            @RequestParam(defaultValue = "", name = "categoryId") String categoryId,
            @RequestBody UpdateCategoryRequest updateCategoryRequest
    ) throws CategoryNotFoundException, CategoryUpdateException {
        if (Objects.equals(categoryId, "")) {
            Response response = Response.builder()
                    .message("category id is empty")
                    .build();
            return ResponseEntity.status(400).body(response);
        }

        UUID id = UUID.fromString(categoryId);
        return ResponseEntity.status(200).body(categoryService.updateCategory(id, updateCategoryRequest));
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<?> deleteCategory(
            @RequestParam(defaultValue = "", name = "categoryId") String categoryId
    ) throws CategoryNotFoundException {
        if (Objects.equals(categoryId, "")) {
            Response response = Response.builder()
                    .message("category id is empty")
                    .build();
            return ResponseEntity.status(400).body(response);
        }

        UUID id = UUID.fromString(categoryId);
        return ResponseEntity.status(200).body(categoryService.deleteCategory(id));
    }
}

