package com.praveenukkoji.productservice.service;

import com.praveenukkoji.productservice.dto.AllProductDetailResponse;
import com.praveenukkoji.productservice.dto.ProductCreateRequest;
import com.praveenukkoji.productservice.dto.ProductDetailResponse;
import com.praveenukkoji.productservice.exception.product.ProductNotFoundException;
import com.praveenukkoji.productservice.exception.product.UnableToCreateProductException;
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

    public Map<UUID, Integer> getQty(List<UUID> product_ids) {
        try {
            ResponseEntity<Map<UUID, Integer>> response = inventoryClient.getQty(product_ids);

            if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                return response.getBody();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return Collections.emptyMap();
    }

    public UUID createProduct(ProductCreateRequest productCreateRequest)
            throws UnableToCreateProductException {

        Product entity = Product.builder()
                .product_name(productCreateRequest.getProduct_name())
                .product_desc(productCreateRequest.getProduct_desc())
                .product_price(productCreateRequest.getProduct_price())
                .created_on(LocalDate.now())
                .created_by(productCreateRequest.getCreated_by())
                .modified_by(productCreateRequest.getCreated_by())
                .build();

        try {
            Product queryResult = productRepository.saveAndFlush(entity);
            log.info("create_product - product created successfully with id = {}", queryResult.getProduct_id());

            return queryResult.getProduct_id();
        } catch (Exception e) {
            throw new UnableToCreateProductException("create_product - unable to save product");
        }
    }

    public ProductDetailResponse getProduct(UUID product_id)
            throws ProductNotFoundException {

        Optional<Product> queryResult = productRepository.findById(product_id);

        if (queryResult.isPresent()) {
            Product entity = queryResult.get();
            log.info("get_product - product fetched with id = {}", product_id);

            List<UUID> product_id_list = new ArrayList<>();
            product_id_list.add(product_id);

            Map<UUID, Integer> qty = getQty(product_id_list);

            return ProductDetailResponse.builder()
                    .product_id(entity.getProduct_id())
                    .product_name(entity.getProduct_name())
                    .product_desc(entity.getProduct_desc())
                    .product_price(entity.getProduct_price())
                    .product_qty(qty.getOrDefault(product_id, 0))
                    .created_on(entity.getCreated_on())
                    .created_by(entity.getCreated_by())
                    .modified_by(entity.getModified_by())
                    .build();
        } else {
            throw new ProductNotFoundException("get_product - product not found");
        }
    }

    public AllProductDetailResponse getAllProduct() {

        List<Product> queryResult = productRepository.findAll();
        log.info("get_all_product - fetched all products");

        List<UUID> product_id_list = new ArrayList<>();
        product_id_list = queryResult.stream().map(Product::getProduct_id).toList();

        Map<UUID, Integer> qty = getQty(product_id_list);

        List<ProductDetailResponse> products = queryResult.stream().map(entity -> {
            return ProductDetailResponse.builder()
                    .product_id(entity.getProduct_id())
                    .product_name(entity.getProduct_name())
                    .product_desc(entity.getProduct_desc())
                    .product_price(entity.getProduct_price())
                    .product_qty(qty.getOrDefault(entity.getProduct_id(), 0))
                    .created_on(entity.getCreated_on())
                    .created_by(entity.getCreated_by())
                    .modified_by(entity.getModified_by())
                    .build();
        }).toList();

        return AllProductDetailResponse.builder()
                .total_products(products.size())
                .product_list(products)
                .build();
    }

    public Boolean deleteProduct(UUID product_id)
            throws ProductNotFoundException {

        Optional<Product> queryResult = productRepository.findById(product_id);

        if (queryResult.isPresent()) {
            productRepository.deleteById(product_id);
            log.info("delete_product - product deleted with id = {}", product_id);

            return true;
        } else {
            throw new ProductNotFoundException("delete_product - product not found");
        }
    }
}
