package com.praveenukkoji.orderservice.utility;

import com.praveenukkoji.orderservice.dto.order.request.Item;
import com.praveenukkoji.orderservice.exception.order.ProductNotFoundException;
import com.praveenukkoji.orderservice.feign.dto.product.request.DecreaseProductStockRequest;
import com.praveenukkoji.orderservice.feign.dto.product.request.ProductDetailRequest;
import com.praveenukkoji.orderservice.feign.dto.product.response.ProductDetailResponse;
import com.praveenukkoji.orderservice.feign.exception.product.ProductServiceException;
import com.praveenukkoji.orderservice.feign.feignClient.product.ProductClient;
import com.praveenukkoji.orderservice.model.OrderItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderUtility {

    private final ProductClient productClient;

    // getting product detail from product-service
    public List<ProductDetailResponse> getProductDetail(List<Item> itemList)
            throws ProductServiceException {

        // creating product-detail-request dto
        List<ProductDetailRequest> productDetailRequest = itemList.stream().map(item ->
                ProductDetailRequest.builder()
                        .productId(UUID.fromString(item.getProductId()))
                        .quantity(item.getQuantity())
                        .build()
        ).toList();

        log.info("fetching product detail from product-service");

        try {
            // Using FeignClient
            ResponseEntity<List<ProductDetailResponse>> productDetailsResponse =
                    productClient.getProductDetails(productDetailRequest);

            if (productDetailsResponse.getStatusCode().value() != 200) {
                throw new ProductServiceException("some error occurred while fetching product detail");
            }

            return productDetailsResponse.getBody();
        } catch (Exception e) {
            throw new ProductServiceException(e.getMessage());
        }
    }

    // get order amount
    public Double getOrderAmount(List<Item> itemList, List<ProductDetailResponse> productDetailList)
            throws ProductNotFoundException {

        log.info("calculating order amount");

        double orderAmount = 0.0;

        // calculating order amount
        for (Item item : itemList) {
            UUID itemId = UUID.fromString(item.getProductId());
            int quantity = item.getQuantity();

            Optional<ProductDetailResponse> matchingProduct = productDetailList.stream()
                    .filter(product -> product.getProductId().equals(itemId))
                    .findFirst();

            if (matchingProduct.isPresent()) {
                double productPrice = matchingProduct.get().getPrice();
                orderAmount += quantity * productPrice;
            } else {
                throw new ProductNotFoundException("product with id = " + itemId + " not found");
            }
        }

        return orderAmount;
    }

    // get order item list
    public List<OrderItem> getOrderItemList(List<Item> itemList, List<ProductDetailResponse> productDetailList)
            throws ProductNotFoundException {

        log.info("creating order item list");

        List<OrderItem> orderItemsList = new ArrayList<>();

        // create order item list
        for (Item item : itemList) {
            UUID itemId = UUID.fromString(item.getProductId());
            int quantity = item.getQuantity();

            Optional<ProductDetailResponse> matchingProduct = productDetailList.stream()
                    .filter(productDetailResponse -> productDetailResponse.getProductId().equals(itemId))
                    .findFirst();

            if (matchingProduct.isPresent()) {
                orderItemsList.add(OrderItem.builder()
                        .productId(itemId)
                        .quantity(quantity)
                        .price(matchingProduct.get().getPrice())
                        .build());
            } else {
                throw new ProductNotFoundException("product with id = " + itemId + " not found");
            }
        }

        return orderItemsList;
    }

    // decreasing product stock through product-service
    public void decreaseProductStock(List<Item> itemList)
            throws ProductServiceException {

        // creating decrease-stock-request dto
        List<DecreaseProductStockRequest> decreaseProductStockRequest = itemList.stream()
                .map(item -> DecreaseProductStockRequest.builder()
                        .productId(UUID.fromString(item.getProductId()))
                        .quantity(item.getQuantity())
                        .build()
                ).toList();
        
        log.info("decreasing product stock through product-service");

        try {
            // Using FeignClient
            ResponseEntity<Boolean> decreaseStockResponse = productClient.decreaseStock(decreaseProductStockRequest);

            if (decreaseStockResponse.getStatusCode().value() != 204) {
                throw new ProductServiceException("some error occurred while decreasing stock");
            }

        } catch (Exception e) {
            throw new ProductServiceException(e.getMessage());
        }
    }
}
