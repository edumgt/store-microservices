package com.praveenukkoji.productservice.controller;

import com.praveenukkoji.productservice.dto.product.request.CreateProductRequest;
import com.praveenukkoji.productservice.dto.product.request.UpdateProductPriceRequest;
import com.praveenukkoji.productservice.dto.product.request.UpdateProductRequest;
import com.praveenukkoji.productservice.exception.category.CategoryNotFoundException;
import com.praveenukkoji.productservice.exception.error.ValidationException;
import com.praveenukkoji.productservice.exception.product.ProductImageNotFoundException;
import com.praveenukkoji.productservice.exception.product.ProductCreateException;
import com.praveenukkoji.productservice.exception.product.ProductDeleteException;
import com.praveenukkoji.productservice.exception.product.ProductNotFoundException;
import com.praveenukkoji.productservice.exception.product.ProductUpdateException;
import com.praveenukkoji.productservice.dto.product.request.DecreaseProductStockRequest;
import com.praveenukkoji.productservice.dto.product.request.IncreaseProductStockRequest;
import com.praveenukkoji.productservice.dto.product.request.ProductDetailRequest;
import com.praveenukkoji.productservice.dto.product.response.ProductDetailResponse;
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
    ) throws CategoryNotFoundException, ProductCreateException, IOException, ValidationException {
        return ResponseEntity.status(201).body(productService.createProduct(createProductRequest, image));
    }

    // get
    @GetMapping(path = "")
    public ResponseEntity<?> getProduct(@RequestParam(defaultValue = "", name = "productId") String productId)
            throws ProductNotFoundException, ValidationException {
        return ResponseEntity.status(200).body(productService.getProduct(productId));
    }

    // update
    @PatchMapping(path = "")
    public ResponseEntity<?> updateProduct(@RequestBody @Valid UpdateProductRequest updateProductRequest)
            throws ProductNotFoundException, ProductUpdateException, ValidationException {
        productService.updateProduct(updateProductRequest);
        return ResponseEntity.status(204).body("");
    }

    // delete
    @DeleteMapping(path = "")
    public ResponseEntity<?> deleteProduct(@RequestParam(defaultValue = "", name = "productId") String productId)
            throws ProductNotFoundException, ProductDeleteException, IOException, ValidationException {
        productService.deleteProduct(productId);
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
    ) throws CategoryNotFoundException, ValidationException {
        return ResponseEntity.status(200).body(productService.getProductByCategory(categoryName));
    }

    // increase stock
    @PatchMapping("/increase-stock")
    public ResponseEntity<?> increaseStock(
            @RequestBody @Valid List<IncreaseProductStockRequest> increaseProductStockRequestList
    ) throws ProductNotFoundException, ProductUpdateException, ValidationException {
        productService.increaseStock(increaseProductStockRequestList);
        return ResponseEntity.status(204).body("");
    }

    // decrease stock
    @PatchMapping("/decrease-stock")
    public ResponseEntity<?> decreaseStock(
            @RequestBody @Valid List<DecreaseProductStockRequest> decreaseProductStockRequestList
    ) throws ProductUpdateException, ProductNotFoundException, ValidationException {
        productService.decreaseStock(decreaseProductStockRequestList);
        return ResponseEntity.status(204).body("");
    }

    // update product price
    @PatchMapping(value = "/update-price")
    public ResponseEntity<?> updateProductPrice(@RequestBody @Valid UpdateProductPriceRequest updateProductPriceRequest
    ) throws ProductUpdateException, ProductNotFoundException, ValidationException {
        productService.updateProductPrice(updateProductPriceRequest);
        return ResponseEntity.status(204).body("");
    }

    // get product details
    @PostMapping(value = "/product-detail")
    public ResponseEntity<List<ProductDetailResponse>> getProductDetails(
            @RequestBody @Valid List<ProductDetailRequest> productDetailRequest
    ) throws ValidationException {
        return ResponseEntity.status(200).body(productService.getProductDetails(productDetailRequest));
    }

    // get image
    @GetMapping(value = "/image")
    public ResponseEntity<?> getProductImage(
            @RequestParam(defaultValue = "", name = "imageId") String imageId
    ) throws ProductImageNotFoundException, IOException, ValidationException {

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

    // get product by pagination
    @GetMapping("/pagination")
    public ResponseEntity<?> getProductByPagination(
            @RequestParam(name = "pageNumber") Integer pageNumber,
            @RequestParam(name = "pageSize") Integer pageSize
    ) throws ValidationException {
        return ResponseEntity.status(200).body(productService.getProductByPagination(pageNumber, pageSize));
    }

    // get product sorted by
    @GetMapping("/sorted")
    public ResponseEntity<?> getProductSortedBy(
            @RequestParam(defaultValue = "", name = "sortingParameter") String sortingParameter,
            @RequestParam(name = "sortDescending", required = false) Boolean sortDescending
    ) throws ValidationException {
        return ResponseEntity.status(200).body(productService.getProductSortedBy(sortingParameter, sortDescending));
    }
}
