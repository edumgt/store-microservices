package com.praveenukkoji.productservice.service;

import com.praveenukkoji.productservice.dto.request.product.CreateProductRequest;
import com.praveenukkoji.productservice.dto.response.category.CategoryResponse;
import com.praveenukkoji.productservice.dto.response.product.ProductResponse;
import com.praveenukkoji.productservice.exception.category.CategoryNotFoundException;
import com.praveenukkoji.productservice.exception.product.ProductCreateException;
import com.praveenukkoji.productservice.exception.product.ProductDeleteException;
import com.praveenukkoji.productservice.exception.product.ProductNotFoundException;
import com.praveenukkoji.productservice.model.Category;
import com.praveenukkoji.productservice.model.Product;
import com.praveenukkoji.productservice.repository.CategoryRepository;
import com.praveenukkoji.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    // create
    public UUID createProduct(CreateProductRequest createProductRequest)
            throws CategoryNotFoundException, ProductCreateException {

        UUID categoryId = createProductRequest.getCategoryId();
        Optional<Category> category = categoryRepository.findById(categoryId);

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
                throw new ProductCreateException(e.getMessage());
            }
        }

        throw new CategoryNotFoundException("category with id = " + categoryId + " not found");
    }

    // retrieve
    public ProductResponse getProduct(UUID productId) throws ProductNotFoundException {

        Optional<Product> product = productRepository.findById(productId);

        if (product.isPresent()) {
            Category productCategory = product.get().getCategory();
            CategoryResponse category = CategoryResponse.builder()
                    .id(productCategory.getId())
                    .name(productCategory.getName())
                    .build();

            return ProductResponse.builder()
                    .id(product.get().getId())
                    .name(product.get().getName())
                    .description(product.get().getDescription())
                    .price(product.get().getPrice())
                    .quantity(product.get().getQuantity())
                    .category(category)
                    .build();
        }

        throw new ProductNotFoundException("product with id = " + productId + " not found");
    }

    // delete
    public void deleteProduct(UUID productId) throws ProductNotFoundException, ProductDeleteException {

        Optional<Product> product = productRepository.findById(productId);

        if (product.isPresent()) {
            try {
                productRepository.deleteById(productId);
                return;
            } catch (Exception e) {
                throw new ProductDeleteException(e.getMessage());
            }
        }

        throw new ProductNotFoundException("product with id = " + productId + " not found");
    }

    // get all
    public List<ProductResponse> getAllProduct() {

        List<Product> productList = productRepository.findAll();

        return productList.stream()
                .map(product -> {
                    Category productCategory = product.getCategory();
                    CategoryResponse category = CategoryResponse.builder()
                            .id(productCategory.getId())
                            .name(productCategory.getName())
                            .build();
                    
                    return ProductResponse.builder()
                            .id(product.getId())
                            .name(product.getName())
                            .description(product.getDescription())
                            .price(product.getPrice())
                            .quantity(product.getQuantity())
                            .category(category)
                            .build();
                })
                .toList();
    }
}
