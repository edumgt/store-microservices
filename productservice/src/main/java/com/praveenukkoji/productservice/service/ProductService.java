package com.praveenukkoji.productservice.service;

import com.praveenukkoji.productservice.dto.request.CreateProductRequest;
import com.praveenukkoji.productservice.dto.response.GetAllProductResponse;
import com.praveenukkoji.productservice.dto.response.GetProductResponse;
import com.praveenukkoji.productservice.dto.response.ProductResponse;
import com.praveenukkoji.productservice.exception.CreateProductException;
import com.praveenukkoji.productservice.exception.ProductNotFoundException;
import com.praveenukkoji.productservice.feign.client.InventoryClient;
import com.praveenukkoji.productservice.model.Product;
import com.praveenukkoji.productservice.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryClient inventoryClient;

    public Map<UUID, Integer> getQtyOfProducts(List<UUID> product_id) {
        try {
            ResponseEntity<Map<UUID, Integer>> response = inventoryClient.getQty(product_id);

            if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                log.info("fetched qty of products");

                return response.getBody();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return Collections.emptyMap();
    }

    public ProductResponse createProduct(CreateProductRequest createProductRequest)
            throws CreateProductException {

        Product product_entity = Product.builder()
                .product_name(createProductRequest.getProduct_name())
                .product_desc(createProductRequest.getProduct_desc())
                .product_price(createProductRequest.getProduct_price())
                .created_on(LocalDate.now())
                .created_by(createProductRequest.getCreated_by())
                .modified_by(createProductRequest.getCreated_by())
                .build();

        try {
            Product queryResult = productRepository.saveAndFlush(product_entity);

            log.info("product created successfully with id = {}", queryResult.getProduct_id());

            return ProductResponse.builder()
                    .message("product created successfully with id = " + queryResult.getProduct_id())
                    .build();
        } catch (Exception e) {
            throw new CreateProductException("unable to create product");
        }
    }

    public GetProductResponse getProduct(UUID product_id)
            throws ProductNotFoundException {

        Optional<Product> queryResult = productRepository.findById(product_id);

        if (queryResult.isPresent()) {
            Product product_entity = queryResult.get();

            log.info("fetched product with id = {}", product_id);

            List<UUID> product_id_list = new ArrayList<>();
            product_id_list.add(product_id);

            Map<UUID, Integer> qty = getQtyOfProducts(product_id_list);

            return GetProductResponse.builder()
                    .product_id(product_entity.getProduct_id())
                    .product_name(product_entity.getProduct_name())
                    .product_desc(product_entity.getProduct_desc())
                    .product_price(product_entity.getProduct_price())
                    .product_qty(qty.getOrDefault(product_id, 0))
                    .created_on(product_entity.getCreated_on())
                    .created_by(product_entity.getCreated_by())
                    .modified_by(product_entity.getModified_by())
                    .build();
        } else {
            throw new ProductNotFoundException("product not found");
        }
    }

    public GetAllProductResponse getAllProduct() {

        List<Product> queryResult = productRepository.findAll();

        log.info("fetched all products");

        List<UUID> product_id_list = queryResult.stream().map(Product::getProduct_id).toList();

        Map<UUID, Integer> qty = getQtyOfProducts(product_id_list);

        List<GetProductResponse> products = queryResult.stream().map(entity -> GetProductResponse.builder()
                .product_id(entity.getProduct_id())
                .product_name(entity.getProduct_name())
                .product_desc(entity.getProduct_desc())
                .product_price(entity.getProduct_price())
                .product_qty(qty.getOrDefault(entity.getProduct_id(), 0))
                .created_on(entity.getCreated_on())
                .created_by(entity.getCreated_by())
                .modified_by(entity.getModified_by())
                .build()).toList();

        return GetAllProductResponse.builder()
                .total_products(products.size())
                .products(products)
                .build();
    }

    public ProductResponse deleteProduct(UUID product_id)
            throws ProductNotFoundException {

        Optional<Product> queryResult = productRepository.findById(product_id);

        if (queryResult.isPresent()) {
            productRepository.deleteById(product_id);

            log.info("product deleted with id = {}", product_id);

            return ProductResponse.builder()
                    .message("product deleted with id = " + product_id)
                    .build();
        } else {
            throw new ProductNotFoundException("product not found");
        }
    }
}
