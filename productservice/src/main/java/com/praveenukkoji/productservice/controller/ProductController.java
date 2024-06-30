package com.praveenukkoji.productservice.controller;

import com.praveenukkoji.productservice.dto.request.CreateProductRequest;
import com.praveenukkoji.productservice.dto.response.GetAllProductResponse;
import com.praveenukkoji.productservice.dto.response.GetProductResponse;
import com.praveenukkoji.productservice.exception.CreateProductException;
import com.praveenukkoji.productservice.exception.ProductNotFoundException;
import com.praveenukkoji.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping(path = "/create")
    public ResponseEntity<UUID> createProduct(@RequestBody CreateProductRequest createProductRequest)
            throws CreateProductException {
        return ResponseEntity.status(201).body(productService.createProduct(createProductRequest));
    }

    @GetMapping(path = "/get/{productId}")
    public ResponseEntity<GetProductResponse> getProduct(@PathVariable UUID productId)
            throws ProductNotFoundException {
        return ResponseEntity.status(200).body(productService.getProduct(productId));
    }

    @GetMapping(path = "/get/all")
    public ResponseEntity<GetAllProductResponse> getAllProduct() {
        return ResponseEntity.status(200).body(productService.getAllProduct());
    }

    @DeleteMapping(path = "/delete/{productId}")
    public ResponseEntity<UUID> deleteProduct(@PathVariable UUID productId)
            throws ProductNotFoundException {
        return ResponseEntity.status(200).body(productService.deleteProduct(productId));
    }
}
