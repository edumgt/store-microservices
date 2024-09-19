package com.praveenukkoji.productservice.controller;

import com.praveenukkoji.productservice.dto.error.ValidationResponse;
import com.praveenukkoji.productservice.dto.request.product.CreateProductRequest;
import com.praveenukkoji.productservice.exception.category.CategoryNotFoundException;
import com.praveenukkoji.productservice.exception.product.ProductCreateException;
import com.praveenukkoji.productservice.exception.product.ProductDeleteException;
import com.praveenukkoji.productservice.exception.product.ProductNotFoundException;
import com.praveenukkoji.productservice.service.ProductService;
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
@RequestMapping(path = "/api/v1/products")
public class ProductController {

    private final ProductService productService;

    // create
    @PostMapping(path = "")
    public ResponseEntity<?> createProduct(@RequestBody @Valid CreateProductRequest createProductRequest)
            throws CategoryNotFoundException, ProductCreateException {
        return ResponseEntity.status(201).body(productService.createProduct(createProductRequest));
    }

    // retrieve
    @GetMapping(path = "")
    public ResponseEntity<?> getProduct(
            @RequestParam(defaultValue = "", name = "productId") String productId
    ) throws ProductNotFoundException {
        if (Objects.equals(productId, "")) {
            Map<String, String> error = new HashMap<>();
            error.put("productId", "product id is empty");

            ValidationResponse response = ValidationResponse.builder()
                    .error(error)
                    .build();

            return ResponseEntity.status(400).body(response);
        }

        UUID id = UUID.fromString(productId);
        return ResponseEntity.status(200).body(productService.getProduct(id));
    }

    // update
    /*
        TODO:
        1. create endpoint for product update,
        2. create separate endpoint for price update
        3. create separate endpoint for quantity update
    */

    // delete
    @DeleteMapping(path = "")
    public ResponseEntity<?> deleteProduct(
            @RequestParam(defaultValue = "", name = "productId") String productId
    ) throws ProductNotFoundException, ProductDeleteException {
        if (Objects.equals(productId, "")) {
            Map<String, String> error = new HashMap<>();
            error.put("productId", "product id is empty");

            ValidationResponse response = ValidationResponse.builder()
                    .error(error)
                    .build();

            return ResponseEntity.status(400).body(response);
        }

        UUID id = UUID.fromString(productId);
        productService.deleteProduct(id);

        return ResponseEntity.status(204).body("");
    }

    // get all
    @GetMapping(path = "/all")
    public ResponseEntity<?> getAllProduct() {
        return ResponseEntity.status(200).body(productService.getAllProduct());
    }
}
