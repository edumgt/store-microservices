package com.praveenukkoji.productservice.controller;

import com.praveenukkoji.productservice.dto.AllProductDetailResponse;
import com.praveenukkoji.productservice.dto.ProductCreateRequest;
import com.praveenukkoji.productservice.dto.ProductDetailResponse;
import com.praveenukkoji.productservice.exception.product.ProductNotFoundException;
import com.praveenukkoji.productservice.exception.product.UnableToCreateProductException;
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
    public ResponseEntity<UUID> createProduct(@RequestBody ProductCreateRequest productCreateRequest)
            throws UnableToCreateProductException {
        return ResponseEntity.status(201).body(productService.createProduct(productCreateRequest));
    }

    @GetMapping(path = "/get/{product_id}")
    public ResponseEntity<ProductDetailResponse> getProduct(@PathVariable UUID product_id)
            throws ProductNotFoundException {
        return ResponseEntity.status(200).body(productService.getProduct(product_id));
    }

    @GetMapping(path = "/get/all")
    public ResponseEntity<AllProductDetailResponse> getAllProduct() {
        return ResponseEntity.status(200).body(productService.getAllProduct());
    }

    @DeleteMapping(path = "/delete/{product_id}")
    public ResponseEntity<Boolean> deleteProduct(@PathVariable UUID product_id)
            throws ProductNotFoundException {
        return ResponseEntity.status(200).body(productService.deleteProduct(product_id));
    }
}
