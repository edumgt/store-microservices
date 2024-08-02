package com.praveenukkoji.productservice.service;

import com.praveenukkoji.productservice.dto.Response;
import com.praveenukkoji.productservice.dto.request.product.CreateProductRequest;
import com.praveenukkoji.productservice.dto.response.product.ProductResponse;
import com.praveenukkoji.productservice.exception.product.CreateProductException;
import com.praveenukkoji.productservice.exception.product.ProductNotFoundException;
import com.praveenukkoji.productservice.model.Product;
import com.praveenukkoji.productservice.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductResponse createProduct(CreateProductRequest createProductRequest)
            throws CreateProductException {
        Product newProduct = Product.builder()
                .productName(createProductRequest.getProductName())
                .productDescription(createProductRequest.getProductDescription())
                .productPrice(createProductRequest.getProductPrice())
                .productQuantity(createProductRequest.getProductQuantity())
                .createdOn(LocalDateTime.now())
                .createdBy(createProductRequest.getCreatedBy())
                .build();

        try {
            Product product = productRepository.saveAndFlush(newProduct);

            return ProductResponse.builder()
                    .productId(product.getProductId())
                    .productName(product.getProductName())
                    .productDescription(product.getProductDescription())
                    .productPrice(product.getProductPrice())
                    .productQuantity(product.getProductQuantity())
                    .build();
        } catch (Exception e) {
            throw new CreateProductException();
        }
    }

    public ProductResponse getProduct(UUID productId)
            throws ProductNotFoundException {
        Optional<Product> product = productRepository.findById(productId);

        if (product.isPresent()) {

            return ProductResponse.builder()
                    .productId(product.get().getProductId())
                    .productName(product.get().getProductName())
                    .productDescription(product.get().getProductDescription())
                    .productPrice(product.get().getProductPrice())
                    .productQuantity(product.get().getProductQuantity())
                    .build();
        }

        throw new ProductNotFoundException();
    }

    public List<ProductResponse> getAllProduct() {
        List<Product> productList = productRepository.findAll();

        return productList.stream()
                .map(product -> {
                    return ProductResponse.builder()
                            .productId(product.getProductId())
                            .productName(product.getProductName())
                            .productDescription(product.getProductDescription())
                            .productPrice(product.getProductPrice())
                            .productQuantity(product.getProductQuantity())
                            .build();
                })
                .toList();
    }

    public Response deleteProduct(UUID productId)
            throws ProductNotFoundException {
        Optional<Product> product = productRepository.findById(productId);

        if (product.isPresent()) {
            productRepository.deleteById(productId);

            return Response.builder()
                    .message("product deleted with productId = " + productId)
                    .build();
        }

        throw new ProductNotFoundException();
    }
}
