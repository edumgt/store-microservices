package com.praveenukkoji.productservice.service;

import com.praveenukkoji.productservice.dto.request.product.CreateProductRequest;
import com.praveenukkoji.productservice.dto.request.product.UpdateProductPriceRequest;
import com.praveenukkoji.productservice.dto.response.category.CategoryResponse;
import com.praveenukkoji.productservice.dto.response.product.ProductResponse;
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
import com.praveenukkoji.productservice.model.Category;
import com.praveenukkoji.productservice.model.Product;
import com.praveenukkoji.productservice.repository.CategoryRepository;
import com.praveenukkoji.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
@Transactional
@Slf4j
@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final FileStorageService fileStorageService;

    // create
    public UUID createProduct(CreateProductRequest createProductRequest, MultipartFile image)
            throws CategoryNotFoundException, ProductCreateException, IOException {

        log.info("creating new product {}", createProductRequest);

        UUID categoryId = UUID.fromString(createProductRequest.getCategoryId());
        Optional<Category> category = categoryRepository.findById(categoryId);

        if (category.isPresent()) {
            String imageName = "";

            // saving product image
            if (image != null && !image.isEmpty()) {
                imageName = fileStorageService.storeFile(image);
            }

            Product newProduct = Product.builder()
                    .name(createProductRequest.getName())
                    .description(createProductRequest.getDescription())
                    .price(createProductRequest.getPrice())
                    .quantity(createProductRequest.getQuantity())
                    .imageName(imageName)
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

    // get
    public ProductResponse getProduct(UUID productId) throws ProductNotFoundException {

        log.info("fetching product having id = {}", productId);

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
                    .imageName(product.get().getImageName())
                    .build();
        }

        throw new ProductNotFoundException("product with id = " + productId + " not found");
    }

    // delete
    public void deleteProduct(UUID productId) throws ProductNotFoundException, ProductDeleteException, IOException {

        log.info("deleting product having id = {}", productId);

        Optional<Product> product = productRepository.findById(productId);

        if (product.isPresent()) {

            // deleting file
            String imageName = product.get().getImageName();
            if (!Objects.equals(imageName, "")) {
                fileStorageService.deleteFile(imageName);
            }

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

        log.info("fetching all products");

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
                            .imageName(product.getImageName())
                            .category(category)
                            .build();
                })
                .toList();
    }

    // get by category
    public List<ProductResponse> getProductByCategory(String categoryName)
            throws CategoryNotFoundException {

        log.info("fetch all products having category =  {}", categoryName);

        Optional<Category> category = categoryRepository.findByCategoryName(categoryName);

        if (category.isPresent()) {
            List<Product> productList = productRepository.findAllByCategoryName(category.get());

            CategoryResponse productCategory = CategoryResponse.builder()
                    .id(category.get().getId())
                    .name(category.get().getName())
                    .build();

            return productList.stream().map(product -> ProductResponse.builder()
                            .id(product.getId())
                            .name(product.getName())
                            .description(product.getDescription())
                            .price(product.getPrice())
                            .quantity(product.getQuantity())
                            .imageName(product.getImageName())
                            .category(productCategory)
                            .build())
                    .toList();
        }

        throw new CategoryNotFoundException(categoryName + " category not found");
    }

    // increase stock
    public void increaseStock(List<IncreaseProductStockRequest> increaseProductStockRequestList)
            throws ProductNotFoundException, ProductUpdateException {

        log.info("increase stock request");

        List<UUID> productIdList = increaseProductStockRequestList.stream().map(
                        product -> UUID.fromString(product.getProductId()))
                .toList();

        List<Product> productList = productRepository.findAllById(productIdList);

        // updated product list
        List<Product> updatedProductList = new ArrayList<>();

        for (IncreaseProductStockRequest item : increaseProductStockRequestList) {
            UUID itemId = UUID.fromString(item.getProductId());
            Integer increaseStockValue = item.getQuantity();

            if (increaseStockValue < 1) {
                throw new ProductUpdateException("increase stock value cannot be less than 1");
            }

            Optional<Product> matchingProduct = productList.stream()
                    .filter(product -> product.getId().equals(itemId))
                    .findFirst();

            if (matchingProduct.isPresent()) {
                int updatedValueOfStock = matchingProduct.get().getQuantity() + increaseStockValue;

                // updating stock value
                Product updatedProduct = matchingProduct.get();
                updatedProduct.setQuantity(updatedValueOfStock);

                updatedProductList.add(updatedProduct);
            } else {
                throw new ProductNotFoundException("product with id = " + itemId + " not found");
            }
        }

        // commiting updatedProductList to db
        try {
            productRepository.saveAll(updatedProductList);
        } catch (Exception e) {
            throw new ProductUpdateException(e.getMessage());
        }
    }

    // decrease stock
    public void decreaseStock(List<DecreaseProductStockRequest> decreaseProductStockRequestList)
            throws ProductNotFoundException, ProductUpdateException {

        log.info("decrease stock request");

        List<UUID> productIdList = decreaseProductStockRequestList.stream().map(
                        product -> UUID.fromString(product.getProductId())
                )
                .toList();

        List<Product> productList = productRepository.findAllById(productIdList);

        // Check stock availability before updating
        for (DecreaseProductStockRequest item : decreaseProductStockRequestList) {
            UUID itemId = UUID.fromString(item.getProductId());
            Integer decreaseStockValue = item.getQuantity();

            Optional<Product> matchingProduct = productList.stream()
                    .filter(product -> product.getId().equals(itemId))
                    .findFirst();

            if (matchingProduct.isPresent()) {
                if (matchingProduct.get().getQuantity() == 0 ||
                        matchingProduct.get().getQuantity() < decreaseStockValue) {
                    throw new ProductUpdateException("remaining stock = " + matchingProduct.get().getQuantity() + " for product id = " + itemId);
                }
            } else {
                throw new ProductNotFoundException("product with id = " + itemId + " not found");
            }
        }

        // updated product list
        List<Product> updatedProductList = new ArrayList<>();

        for (DecreaseProductStockRequest item : decreaseProductStockRequestList) {
            UUID itemId = UUID.fromString(item.getProductId());
            Integer decreaseStockValue = item.getQuantity();

            Optional<Product> matchingProduct = productList.stream()
                    .filter(product -> product.getId().equals(itemId))
                    .findFirst();

            if (matchingProduct.isPresent()) {
                int updatedValueOfStock = matchingProduct.get().getQuantity() - decreaseStockValue;

                // updating stock value
                Product updatedProduct = matchingProduct.get();
                updatedProduct.setQuantity(updatedValueOfStock);

                updatedProductList.add(updatedProduct);
            } else {
                throw new ProductNotFoundException("product with id = " + itemId + " not found");
            }
        }

        // commiting updatedProductList to db
        try {
            productRepository.saveAll(updatedProductList);
        } catch (Exception e) {
            throw new ProductUpdateException(e.getMessage());
        }
    }

    // update product price
    public UUID updateProductPrice(UpdateProductPriceRequest updateProductPriceRequest)
            throws ProductNotFoundException, ProductUpdateException {
        UUID productId = UUID.fromString(updateProductPriceRequest.getProductId());
        Double newPrice = updateProductPriceRequest.getProductPrice();

        if (newPrice <= 0.0) {
            throw new ProductUpdateException("product price cannot be zero or less than zero");
        }

        log.info("updating price of product having id = {}", productId);

        Optional<Product> product = productRepository.findById(productId);

        if (product.isPresent()) {
            try {
                Product updatedProduct = product.get();
                updatedProduct.setPrice(newPrice);

                return productRepository.save(updatedProduct).getId();
            } catch (Exception e) {
                throw new ProductUpdateException(e.getMessage());
            }
        }

        throw new ProductNotFoundException("product with id = " + productId + " not found");
    }

    // fetch product details
    public List<ProductDetailResponse> getProductDetails(List<ProductDetailRequest> productDetailRequest) {

        log.info("fetching product details");

        List<ProductDetailResponse> productDetailResponse = new ArrayList<>();

        List<UUID> productIdList = productDetailRequest.stream().map(
                product -> UUID.fromString(product.getProductId())
        ).toList();

        List<Product> productList = productRepository.findAllById(productIdList);

        if (!productList.isEmpty()) {
            productDetailRequest.forEach(requestedProduct -> {
                UUID productId = UUID.fromString(requestedProduct.getProductId());

                Optional<Product> matchingProduct = productList.stream()
                        .filter(product -> product.getId().equals(productId))
                        .findFirst();

                if (matchingProduct.isPresent()) {
                    productDetailResponse.add(ProductDetailResponse.builder()
                            .productId(productId)
                            .price(matchingProduct.get().getPrice())
                            .inStock(matchingProduct.get().getQuantity() >= requestedProduct.getQuantity())
                            .build()
                    );
                } else {
                    productDetailResponse.add(ProductDetailResponse.builder()
                            .productId(productId)
                            .price(0.0)
                            .inStock(false)
                            .build()
                    );
                }
            });
        } else {
            productDetailRequest.forEach(requestedProduct -> {
                productDetailResponse.add(ProductDetailResponse.builder()
                        .productId(UUID.fromString(requestedProduct.getProductId()))
                        .price(0.0)
                        .inStock(false)
                        .build());
            });
        }

        return productDetailResponse;
    }

    // fetch image
    public Resource getProductImage(String imageId) throws ImageNotFoundException {
        log.info("fetching image having id = {}", imageId);

        return fileStorageService.getImage(imageId);
    }
}
