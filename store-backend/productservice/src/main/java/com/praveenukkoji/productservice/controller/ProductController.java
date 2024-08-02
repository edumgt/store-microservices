package com.praveenukkoji.productservice.controller;

import com.praveenukkoji.productservice.dto.Response;
import com.praveenukkoji.productservice.dto.request.product.CreateProductRequest;
import com.praveenukkoji.productservice.exception.product.CreateProductException;
import com.praveenukkoji.productservice.exception.product.ProductNotFoundException;
import com.praveenukkoji.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping(path = "/create")
    public ResponseEntity<?> createProduct(@RequestBody CreateProductRequest createProductRequest)
            throws CreateProductException {
        return ResponseEntity.status(201).body(productService.createProduct(createProductRequest));
    }

    @GetMapping(path = "/get")
    public ResponseEntity<?> getProduct(
            @RequestParam(defaultValue = "", name = "productId") String productId)
            throws ProductNotFoundException {

        if (Objects.equals(productId, "")) {
            Response response = Response.builder()
                    .message("product id is empty")
                    .build();

            return ResponseEntity.status(400).body(response);
        }

        UUID id = UUID.fromString(productId);
        return ResponseEntity.status(200).body(productService.getProduct(id));
    }

    @GetMapping(path = "/get/all")
    public ResponseEntity<?> getAllProduct() {
        return ResponseEntity.status(200).body(productService.getAllProduct());
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<?> deleteProduct(
            @RequestParam(defaultValue = "", name = "productId") String productId
    ) throws ProductNotFoundException {

        if (Objects.equals(productId, "")) {
            Response response = Response.builder()
                    .message("product id is empty")
                    .build();

            return ResponseEntity.status(400).body(response);
        }

        UUID id = UUID.fromString(productId);
        return ResponseEntity.status(200).body(productService.deleteProduct(id));
    }
}
