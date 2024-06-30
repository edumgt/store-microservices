package com.praveenukkoji.productservice.service;

import com.praveenukkoji.productservice.dto.request.CreateProductRequest;
import com.praveenukkoji.productservice.dto.response.GetAllProductResponse;
import com.praveenukkoji.productservice.dto.response.GetProductResponse;
import com.praveenukkoji.productservice.exception.CreateProductException;
import com.praveenukkoji.productservice.exception.ProductNotFoundException;
import com.praveenukkoji.productservice.model.Product;
import com.praveenukkoji.productservice.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public UUID createProduct(CreateProductRequest createProductRequest)
            throws CreateProductException {
        Product newProduct = Product.builder()
                .productName(createProductRequest.getProductName())
                .productDesc(createProductRequest.getProductDesc())
                .productPrice(createProductRequest.getProductPrice())
                .productQty(createProductRequest.getProductQty())
                .createdOn(LocalDate.now())
                .createdBy(createProductRequest.getCreatedBy())
                .modifiedBy(createProductRequest.getCreatedBy())
                .build();

        try {
            Product product = productRepository.saveAndFlush(newProduct);
            log.info("product created with id = {}", product.getProductId());
            return product.getProductId();
        } catch (Exception e) {
            throw new CreateProductException("unable to create product");
        }
    }

    public GetProductResponse getProduct(UUID productId)
            throws ProductNotFoundException {
        Optional<Product> queryResult = productRepository.findById(productId);

        if (queryResult.isPresent()) {
            Product product = queryResult.get();
            log.info("product fetched with id = {}", productId);

            return GetProductResponse.builder()
                    .productId(product.getProductId())
                    .productName(product.getProductName())
                    .productDesc(product.getProductDesc())
                    .productPrice(product.getProductPrice())
                    .productQty(product.getProductQty())
                    .createdOn(product.getCreatedOn())
                    .createdBy(product.getCreatedBy())
                    .modifiedBy(product.getModifiedBy())
                    .build();
        }

        throw new ProductNotFoundException("product not found");
    }

    public GetAllProductResponse getAllProduct() {
        List<Product> queryResult = productRepository.findAll();
        log.info("fetched all products");

        List<GetProductResponse> products = queryResult.stream().map(product -> GetProductResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .productDesc(product.getProductDesc())
                .productPrice(product.getProductPrice())
                .productQty(product.getProductQty())
                .createdOn(product.getCreatedOn())
                .createdBy(product.getCreatedBy())
                .modifiedBy(product.getModifiedBy())
                .build()).toList();

        return GetAllProductResponse.builder()
                .total_products(products.size())
                .products(products)
                .build();
    }

    public UUID deleteProduct(UUID productId)
            throws ProductNotFoundException {
        Optional<Product> queryResult = productRepository.findById(productId);

        if (queryResult.isPresent()) {
            productRepository.deleteById(productId);
            log.info("product deleted with id = {}", productId);

            return productId;
        } else {
            throw new ProductNotFoundException("product not found");
        }
    }
}
