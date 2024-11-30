package com.praveenukkoji.productservice.service;

import com.praveenukkoji.productservice.dto.product.request.CreateProductRequest;
import com.praveenukkoji.productservice.dto.product.request.UpdateProductPriceRequest;
import com.praveenukkoji.productservice.dto.product.request.UpdateProductRequest;
import com.praveenukkoji.productservice.dto.category.response.CategoryResponse;
import com.praveenukkoji.productservice.dto.product.response.ProductResponse;
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
import com.praveenukkoji.productservice.model.Category;
import com.praveenukkoji.productservice.model.Product;
import com.praveenukkoji.productservice.repository.CategoryRepository;
import com.praveenukkoji.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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
            throws CategoryNotFoundException, ProductCreateException, IOException, ValidationException {

        log.info("creating new product {}", createProductRequest);

        String id = createProductRequest.getCategoryId();
        String name = createProductRequest.getName();
        String description = createProductRequest.getDescription();

        if(Objects.equals(id, "")) {
            throw new ValidationException("categoryId", "category id cannot be empty");
        }

        if(Objects.equals(name, "")) {
            throw new ValidationException("name", "product name cannot be empty");
        }

        if(Objects.equals(description, "")) {
            throw new ValidationException("description", "product description cannot be empty");
        }

        try {
            UUID categoryId = UUID.fromString(id);
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

                return productRepository.save(newProduct).getId();
            } else {
                throw new CategoryNotFoundException("category with id = " + categoryId + " not found");
            }
        }
        catch (CategoryNotFoundException e) {
            throw new CategoryNotFoundException(e.getMessage());
        }
        catch (Exception e) {
            throw new ProductCreateException(e.getMessage());
        }
    }

    // get
    public ProductResponse getProduct(String id) throws ProductNotFoundException, ValidationException {

        log.info("fetching product having id = {}", id);

        if(Objects.equals(id, "")) {
            throw new ValidationException("productId", "product id cannot be empty");
        }

        UUID productId = UUID.fromString(id);

        Optional<Product> product = productRepository.findById(productId);

        if (product.isPresent()) {
            String productCategory = product.get().getCategory().getName();

            return ProductResponse.builder()
                    .id(product.get().getId())
                    .name(product.get().getName())
                    .description(product.get().getDescription())
                    .price(product.get().getPrice())
                    .quantity(product.get().getQuantity())
                    .categoryName(productCategory)
                    .imageName(product.get().getImageName())
                    .build();
        }

        throw new ProductNotFoundException("product with id = " + id + " not found");
    }

    // update
    public void updateProduct(UpdateProductRequest updateProductRequest)
            throws ProductNotFoundException, ProductUpdateException, ValidationException {

        String id = updateProductRequest.getProductId();

        log.info("updating product having id = {}", id);

        if(Objects.equals(id, "")) {
            throw new ValidationException("productId", "product id cannot be empty");
        }

        try{
            UUID productId = UUID.fromString(id);
            Optional<Product> product = productRepository.findById(productId);

            if (product.isPresent()) {
                String name = updateProductRequest.getName();
                String description = updateProductRequest.getDescription();

                Product updatedProduct = product.get();

                if (!Objects.equals(name, "")) {
                    updatedProduct.setName(name);
                }

                if (!Objects.equals(description, "")) {
                    updatedProduct.setDescription(description);
                }

                productRepository.save(updatedProduct);
            }
            else {
                throw new ProductNotFoundException("product with id = " + id + " not found");
            }
        }
        catch (ProductNotFoundException e) {
            throw new ProductNotFoundException(e.getMessage());
        }
        catch (Exception e) {
            throw new ProductUpdateException(e.getMessage());
        }
    }

    // delete
    public void deleteProduct(String id)
            throws ProductNotFoundException, ProductDeleteException, IOException, ValidationException {

        log.info("deleting product having id = {}", id);

        if(Objects.equals(id, "")) {
            throw new ValidationException("productId", "product id cannot be empty");
        }

        try {
            UUID productId = UUID.fromString(id);
            Optional<Product> product = productRepository.findById(productId);

            if (product.isPresent()) {

                // deleting file
                String imageName = product.get().getImageName();
                if (!Objects.equals(imageName, "")) {
                    fileStorageService.deleteFile(imageName);
                }

                productRepository.deleteById(productId);
            }
            else {
                throw new ProductNotFoundException("product with id = " + id + " not found");
            }
        }
        catch (ProductNotFoundException e) {
            throw new ProductNotFoundException(e.getMessage());
        }
        catch (Exception e) {
            throw new ProductDeleteException(e.getMessage());
        }
    }

    // get all
    public List<ProductResponse> getAllProduct() {

        log.info("fetching all products");

        List<Product> productList = productRepository.findAll();

        return productList.stream()
                .map(product -> {
                    String productCategory = product.getCategory().getName();

                    return ProductResponse.builder()
                            .id(product.getId())
                            .name(product.getName())
                            .description(product.getDescription())
                            .price(product.getPrice())
                            .quantity(product.getQuantity())
                            .imageName(product.getImageName())
                            .categoryName(productCategory)
                            .build();
                })
                .toList();
    }

    // get by category
    public List<ProductResponse> getProductByCategory(String categoryName)
            throws CategoryNotFoundException, ValidationException {

        log.info("fetch all products having category =  {}", categoryName);

        if(Objects.equals(categoryName, "")) {
            throw new ValidationException("categoryName", "category name cannot be empty");
        }

        Optional<Category> category = categoryRepository.findByCategoryName(categoryName);

        if (category.isPresent()) {
            List<Product> productList = productRepository.findAllByCategory(category.get());

            String productCategory = category.get().getName();

            return productList.stream().map(product -> ProductResponse.builder()
                            .id(product.getId())
                            .name(product.getName())
                            .description(product.getDescription())
                            .price(product.getPrice())
                            .quantity(product.getQuantity())
                            .imageName(product.getImageName())
                            .categoryName(productCategory)
                            .build())
                    .toList();
        }

        throw new CategoryNotFoundException("category with name = "+ categoryName + " not found");
    }

    // increase stock
    public void increaseStock(List<IncreaseProductStockRequest> increaseProductStockRequestList)
            throws ProductNotFoundException, ProductUpdateException, ValidationException {

        log.info("increase stock request");

        if(increaseProductStockRequestList.isEmpty()) {
            throw new ValidationException("increaseStockRequestList", "increase stock request list cannot be empty");
        }

        try {
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
            productRepository.saveAll(updatedProductList);
        }
        catch (ProductNotFoundException e) {
            throw new ProductNotFoundException(e.getMessage());
        }
        catch (Exception e) {
            throw new ProductUpdateException(e.getMessage());
        }
    }

    // decrease stock
    public void decreaseStock(List<DecreaseProductStockRequest> decreaseProductStockRequestList)
            throws ProductNotFoundException, ProductUpdateException, ValidationException {

        log.info("decrease stock request");

        if(decreaseProductStockRequestList.isEmpty()) {
            throw new ValidationException("decreaseStockRequestList", "decrease stock request list cannot be empty");
        }

        try {
            List<UUID> productIdList = decreaseProductStockRequestList.stream().map(
                            product -> UUID.fromString(product.getProductId()))
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
            productRepository.saveAll(updatedProductList);
        }
        catch (ProductNotFoundException e) {
            throw new ProductNotFoundException(e.getMessage());
        }
        catch (Exception e) {
            throw new ProductUpdateException(e.getMessage());
        }
    }

    // update product price
    public void updateProductPrice(UpdateProductPriceRequest updateProductPriceRequest)
            throws ProductNotFoundException, ProductUpdateException, ValidationException {

        String id = updateProductPriceRequest.getProductId();

        log.info("updating price of product having id = {}", id);

        if(Objects.equals(id, "")) {
            throw new ValidationException("productId", "product id cannot be empty");
        }

        try {
            UUID productId = UUID.fromString(id);

            Double newPrice = updateProductPriceRequest.getProductPrice();
            if (newPrice < 0.0) {
                throw new ProductUpdateException("product price cannot be less than zero");
            }

            Optional<Product> product = productRepository.findById(productId);

            if (product.isPresent()) {
                Product updatedProduct = product.get();
                updatedProduct.setPrice(newPrice);

                productRepository.save(updatedProduct);
            }
            else {
                throw new ProductNotFoundException("product with id = " + id + " not found");
            }
        }
        catch (ProductNotFoundException e) {
            throw new ProductNotFoundException(e.getMessage());
        }
        catch (Exception e) {
            throw new ProductUpdateException(e.getMessage());
        }
    }

    // fetch product details
    public List<ProductDetailResponse> getProductDetails(List<ProductDetailRequest> productDetailRequestList)
            throws ValidationException {

        log.info("fetching product details");

        if(productDetailRequestList.isEmpty()) {
            throw new ValidationException("productDetailRequestList", "product detail request list cannot be empty");
        }

        // product details response array
        List<ProductDetailResponse> productDetailResponse = new ArrayList<>();

        List<UUID> productIdList = productDetailRequestList.stream().map(
                product -> UUID.fromString(product.getProductId())
        ).toList();

        List<Product> productList = productRepository.findAllById(productIdList);

        if (!productList.isEmpty()) {
            productDetailRequestList.forEach(requestedProduct -> {
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
            productDetailRequestList.forEach(requestedProduct -> {
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
    public Resource getProductImage(String imageId) throws ProductImageNotFoundException, ValidationException {
        log.info("fetching image having id = {}", imageId);

        if(Objects.equals(imageId, "")) {
            throw new ValidationException("imageId", "image id cannot be empty");
        }

        return fileStorageService.getImage(imageId);
    }

    // get product by pagination
    public List<ProductResponse> getProductByPagination(Integer pageNumber, Integer pageSize) throws ValidationException {

        log.info("fetching all products with pagination");

        if(pageNumber <= 0) {
            throw new ValidationException("pageNumber", "page number should be greater than 0");
        }

        if(pageSize <= 0) {
            throw new ValidationException("pageSize", "page size should be greater than 0");
        }

        pageNumber = pageNumber - 1; // handling for 0th page number

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return productRepository.findAll(pageable).map(product -> {
            String productCategory = product.getCategory().getName();

            return ProductResponse.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .description(product.getDescription())
                    .price(product.getPrice())
                    .quantity(product.getQuantity())
                    .imageName(product.getImageName())
                    .categoryName(productCategory)
                    .build();
        }).toList();
    }

    // get product sorted by
    public List<ProductResponse> getProductSortedBy(String sortingParameter, Boolean sortDescending)
            throws ValidationException {

        log.info("fetching products by sorting parameter {}", sortingParameter);

        if(sortingParameter.isEmpty()) {
            throw new ValidationException("sortingParameter", "sorting parameter cannot be empty");
        }

        if(sortDescending == null) {
            throw new ValidationException("sortDescending", "sort descending is null");
        }

        Direction direction = sortDescending ? Direction.DESC : Direction.ASC;
        Sort sort = Sort.by(direction, sortingParameter);

        return productRepository.findAll(sort).stream().map(product -> {
            String productCategory = product.getCategory().getName();

            return ProductResponse.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .description(product.getDescription())
                    .price(product.getPrice())
                    .quantity(product.getQuantity())
                    .imageName(product.getImageName())
                    .categoryName(productCategory)
                    .build();
        }).toList();
    }
}
