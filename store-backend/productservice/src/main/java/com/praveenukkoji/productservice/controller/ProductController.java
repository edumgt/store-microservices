package com.praveenukkoji.productservice.controller;

import com.praveenukkoji.productservice.dto.error.ValidationResponse;
import com.praveenukkoji.productservice.dto.request.product.CreateProductRequest;
import com.praveenukkoji.productservice.exception.category.CategoryNotFoundException;
import com.praveenukkoji.productservice.exception.product.ProductCreateException;
import com.praveenukkoji.productservice.exception.product.ProductDeleteException;
import com.praveenukkoji.productservice.exception.product.ProductNotFoundException;
import com.praveenukkoji.productservice.exception.product.ProductUpdateException;
import com.praveenukkoji.productservice.external.product.request.DecreaseProductStockRequest;
import com.praveenukkoji.productservice.external.product.request.ProductDetailRequest;
import com.praveenukkoji.productservice.external.product.response.ProductDetailResponse;
import com.praveenukkoji.productservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    // TODO: create endpoint for product update

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

    //get by category name
    @GetMapping("/get-by-category")
    public ResponseEntity<?> getProductByCategory(
            @RequestParam(defaultValue = "", name = "category") String category
    ) throws CategoryNotFoundException {
        if (Objects.equals(category, "")) {
            Map<String, String> error = new HashMap<>();
            error.put("category", "category name is empty");

            ValidationResponse response = ValidationResponse.builder()
                    .error(error)
                    .build();

            return ResponseEntity.status(400).body(response);
        }

        return ResponseEntity.status(200).body(productService.getProductByCategory(category));
    }

    // increase stock
    @PatchMapping("/increase-stock")
    public ResponseEntity<?> increaseStock(
            @RequestParam(defaultValue = "", name = "productId") String productId,
            @RequestParam(defaultValue = "0", name = "increaseStock") Integer increaseStock
    ) throws ProductNotFoundException, ProductUpdateException {
        if (Objects.equals(productId, "")) {
            Map<String, String> error = new HashMap<>();
            error.put("productId", "product id is empty");

            ValidationResponse response = ValidationResponse.builder()
                    .error(error)
                    .build();

            return ResponseEntity.status(400).body(response);
        }

        if (increaseStock == 0) {
            Map<String, String> error = new HashMap<>();
            error.put("increaseStock", "increase-stock is empty or value should be greater than zero");

            ValidationResponse response = ValidationResponse.builder()
                    .error(error)
                    .build();

            return ResponseEntity.status(400).body(response);
        }

        if (increaseStock < 0) {
            Map<String, String> error = new HashMap<>();
            error.put("increaseStock", "increase-stock value should be greater than 0");

            ValidationResponse response = ValidationResponse.builder()
                    .error(error)
                    .build();

            return ResponseEntity.status(400).body(response);
        }

        UUID id = UUID.fromString(productId);
        return ResponseEntity.status(200).body(productService.increaseStock(id, increaseStock));
    }

    // decrease stock
    @PatchMapping("/decrease-stock")
    public ResponseEntity<?> decreaseStock(
            @RequestBody List<DecreaseProductStockRequest> decreaseProductStockRequestList
    ) throws ProductUpdateException, ProductNotFoundException {
        if (decreaseProductStockRequestList.isEmpty()) {
            return ResponseEntity.status(400).body(false);
        }

        return ResponseEntity.status(200).body(productService.decreaseStock(decreaseProductStockRequestList));
    }

    // update product price
    @PatchMapping(value = "/update-price")
    public ResponseEntity<?> updateProductPrice(
            @RequestParam(defaultValue = "", name = "productId") String productId,
            @RequestParam(defaultValue = "0.0", name = "updatedPrice") Double updatedPrice
    ) throws ProductUpdateException, ProductNotFoundException {
        if (Objects.equals(productId, "")) {
            Map<String, String> error = new HashMap<>();
            error.put("productId", "product id is empty");

            ValidationResponse response = ValidationResponse.builder()
                    .error(error)
                    .build();

            return ResponseEntity.status(400).body(response);
        }

        if (updatedPrice < 0) {
            Map<String, String> error = new HashMap<>();
            error.put("updatedPrice", "updated price should be greater than zero");

            ValidationResponse response = ValidationResponse.builder()
                    .error(error)
                    .build();

            return ResponseEntity.status(400).body(response);
        }

        UUID id = UUID.fromString(productId);
        return ResponseEntity.status(200).body(productService.updateProductPrice(id, updatedPrice));
    }

    // fetch product details
    @PostMapping(value = "/product-detail")
    public ResponseEntity<List<ProductDetailResponse>> getProductDetails(
            @RequestBody List<ProductDetailRequest> productDetailRequest) {
        return ResponseEntity.status(200).body(productService.getProductDetails(productDetailRequest));
    }
}
