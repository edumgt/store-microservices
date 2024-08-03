package com.praveenukkoji.productservice.service;

import com.praveenukkoji.productservice.dto.request.product.CreateProductRequest;
import com.praveenukkoji.productservice.dto.response.product.ProductResponse;
import com.praveenukkoji.productservice.exception.category.CategoryNotFoundException;
import com.praveenukkoji.productservice.exception.product.CreateProductException;
import com.praveenukkoji.productservice.exception.product.ProductNotFoundException;
import com.praveenukkoji.productservice.model.Category;
import com.praveenukkoji.productservice.model.Product;
import com.praveenukkoji.productservice.repository.CategoryRepository;
import com.praveenukkoji.productservice.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public UUID createProduct(CreateProductRequest createProductRequest)
            throws CreateProductException, CategoryNotFoundException {

        Optional<Category> category = categoryRepository.findById(createProductRequest.getCategoryId());

        if (category.isPresent()) {
            Product newProduct = Product.builder()
                    .name(createProductRequest.getName())
                    .description(createProductRequest.getDescription())
                    .price(createProductRequest.getPrice())
                    .quantity(createProductRequest.getQuantity())
                    .category(category.get())
                    .build();

            try {
                return productRepository.save(newProduct).getId();
            } catch (Exception e) {
                throw new CreateProductException();
            }
        }

        throw new CategoryNotFoundException();
    }

    public ProductResponse getProduct(UUID productId)
            throws ProductNotFoundException {

        Optional<Product> product = productRepository.findById(productId);

        if (product.isPresent()) {

            return ProductResponse.builder()
                    .id(product.get().getId())
                    .name(product.get().getName())
                    .description(product.get().getDescription())
                    .price(product.get().getPrice())
                    .quantity(product.get().getQuantity())
                    .category((product.get().getCategory()))
                    .build();
        }

        throw new ProductNotFoundException();
    }

    public List<ProductResponse> getAllProduct() {

        List<Product> productList = productRepository.findAll();

        return productList.stream()
                .map(product -> {
                    return ProductResponse.builder()
                            .id(product.getId())
                            .name(product.getName())
                            .description(product.getDescription())
                            .price(product.getPrice())
                            .quantity(product.getQuantity())
                            .category((product.getCategory()))
                            .build();
                })
                .toList();
    }

    public UUID deleteProduct(UUID productId)
            throws ProductNotFoundException {

        Optional<Product> product = productRepository.findById(productId);

        if (product.isPresent()) {
            productRepository.deleteById(productId);

            return productId;
        }

        throw new ProductNotFoundException();
    }
}
