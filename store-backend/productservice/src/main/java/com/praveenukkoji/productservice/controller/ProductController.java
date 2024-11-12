package com.praveenukkoji.productservice.controller;

import com.praveenukkoji.productservice.dto.error.ValidationResponse;
import com.praveenukkoji.productservice.dto.request.product.CreateProductRequest;
import com.praveenukkoji.productservice.dto.request.product.UpdateProductPriceRequest;
import com.praveenukkoji.productservice.exception.category.CategoryNotFoundException;
import com.praveenukkoji.productservice.exception.image.ImageNotFoundException;
import com.praveenukkoji.productservice.exception.product.ProductCreateException;
import com.praveenukkoji.productservice.exception.product.ProductDeleteException;
import com.praveenukkoji.productservice.exception.product.ProductNotFoundException;
import com.praveenukkoji.productservice.exception.product.ProductUpdateException;
import com.praveenukkoji.productservice.feign.product.request.DecreaseProductStockRequest;
import com.praveenukkoji.productservice.feign.product.request.IncreaseProductStockRequest;
import com.praveenukkoji.productservice.feign.product.request.ProductDetailRequest;
import com.praveenukkoji.productservice.feign.product.response.ProductDetailResponse;
import com.praveenukkoji.productservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/products")
public class ProductController {

    private final ProductService productService;

    // create
    @PostMapping(path = "")
    public ResponseEntity<?> createProduct(
            @ModelAttribute @Valid CreateProductRequest createProductRequest,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws CategoryNotFoundException, ProductCreateException, IOException {
        return ResponseEntity.status(201).body(productService.createProduct(createProductRequest, image));
    }

    // get
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
    ) throws ProductNotFoundException, ProductDeleteException, IOException {
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

    // get by category
    @GetMapping("/get-by-category")
    public ResponseEntity<?> getProductByCategory(
            @RequestParam(defaultValue = "", name = "categoryName") String categoryName
    ) throws CategoryNotFoundException {
        if (Objects.equals(categoryName, "")) {
            Map<String, String> error = new HashMap<>();
            error.put("categoryName", "category name is empty");

            ValidationResponse response = ValidationResponse.builder()
                    .error(error)
                    .build();

            return ResponseEntity.status(400).body(response);
        }

        return ResponseEntity.status(200).body(productService.getProductByCategory(categoryName));
    }

    // increase stock
    @PatchMapping("/increase-stock")
    public ResponseEntity<?> increaseStock(
            @RequestBody @Valid List<IncreaseProductStockRequest> increaseProductStockRequestList
    ) throws ProductNotFoundException, ProductUpdateException {
        if (increaseProductStockRequestList.isEmpty()) {
            return ResponseEntity.status(400).body("");
        }

        productService.increaseStock(increaseProductStockRequestList);
        return ResponseEntity.status(204).body("");
    }

    // decrease stock
    @PatchMapping("/decrease-stock")
    public ResponseEntity<?> decreaseStock(
            @RequestBody @Valid List<DecreaseProductStockRequest> decreaseProductStockRequestList
    ) throws ProductUpdateException, ProductNotFoundException {
        if (decreaseProductStockRequestList.isEmpty()) {
            return ResponseEntity.status(400).body("");
        }

        productService.decreaseStock(decreaseProductStockRequestList);
        return ResponseEntity.status(204).body("");
    }

    // update product price
    @PatchMapping(value = "/update-price")
    public ResponseEntity<?> updateProductPrice(@RequestBody @Valid UpdateProductPriceRequest updateProductPriceRequest
    ) throws ProductUpdateException, ProductNotFoundException {
        return ResponseEntity.status(200).body(productService.updateProductPrice(updateProductPriceRequest));
    }

    // fetch product details
    @PostMapping(value = "/product-detail")
    public ResponseEntity<List<ProductDetailResponse>> getProductDetails(
            @RequestBody @Valid List<ProductDetailRequest> productDetailRequest) {
        return ResponseEntity.status(200).body(productService.getProductDetails(productDetailRequest));
    }

    // fetch image
    @GetMapping(value = "/image")
    public ResponseEntity<?> getProductImage(
            @RequestParam(defaultValue = "", name = "imageId") String imageId
    ) throws ImageNotFoundException, IOException {
        if (Objects.equals(imageId, "")) {
            Map<String, String> error = new HashMap<>();
            error.put("imageId", "image id is empty");

            ValidationResponse response = ValidationResponse.builder()
                    .error(error)
                    .build();

            return ResponseEntity.status(400).body(response);
        }

        Resource resource = productService.getProductImage(imageId);

        // Dynamically determine content type based on file's MIME type
        String contentType = Files.probeContentType(resource.getFile().toPath());
        if (contentType == null) {
            contentType = "application/octet-stream"; // Fallback to a generic type
        }

        return ResponseEntity.status(200)
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
