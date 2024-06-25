package com.praveenukkoji.productservice.controller;

import com.praveenukkoji.productservice.dto.request.CreateProductRequest;
import com.praveenukkoji.productservice.dto.response.GetAllProductResponse;
import com.praveenukkoji.productservice.dto.response.GetProductResponse;
import com.praveenukkoji.productservice.dto.response.ProductResponse;
import com.praveenukkoji.productservice.exception.CreateProductException;
import com.praveenukkoji.productservice.exception.ProductNotFoundException;
import com.praveenukkoji.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

// @RefreshScope only changes property values which are used in service etc. using @Value

@RefreshScope
@RestController
@RequestMapping(path = "/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping(path = "/create")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody CreateProductRequest createProductRequest)
            throws CreateProductException {
        return ResponseEntity.status(201).body(productService.createProduct(createProductRequest));
    }

    @GetMapping(path = "/get/{product_id}")
    public ResponseEntity<GetProductResponse> getProduct(@PathVariable UUID product_id)
            throws ProductNotFoundException {
        return ResponseEntity.status(200).body(productService.getProduct(product_id));
    }

    @GetMapping(path = "/get/all")
    public ResponseEntity<GetAllProductResponse> getAllProduct() {
        return ResponseEntity.status(200).body(productService.getAllProduct());
    }

    @DeleteMapping(path = "/delete/{product_id}")
    public ResponseEntity<ProductResponse> deleteProduct(@PathVariable UUID product_id)
            throws ProductNotFoundException {
        return ResponseEntity.status(200).body(productService.deleteProduct(product_id));
    }
}
