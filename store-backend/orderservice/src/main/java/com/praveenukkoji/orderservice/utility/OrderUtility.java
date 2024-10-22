package com.praveenukkoji.orderservice.utility;

import com.praveenukkoji.orderservice.dto.request.order.Item;
import com.praveenukkoji.orderservice.exception.order.CreateOrderException;
import com.praveenukkoji.orderservice.external.product.dto.request.ProductDetailRequest;
import com.praveenukkoji.orderservice.external.product.dto.response.ProductDetailResponse;
import com.praveenukkoji.orderservice.external.product.exception.ProductServiceException;
import com.praveenukkoji.orderservice.external.product.feignClient.ProductClient;
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

    // get product detail
    public List<ProductDetailResponse> getProductDetail(List<ProductDetailRequest> productDetailRequest)
            throws ProductServiceException {

        log.info("fetching product details from product-service");

        if (productDetailRequest.isEmpty()) {
            throw new ProductServiceException("product detail request is null or empty");
        }

        // Using FeignClient
        ResponseEntity<List<ProductDetailResponse>> productDetailsResponse;

        try {
            productDetailsResponse = productClient.getProductDetails(productDetailRequest);
        } catch (Exception e) {
            throw new ProductServiceException(e.getMessage());
        }

        List<ProductDetailResponse> productList = productDetailsResponse.getBody();

        if (productDetailsResponse.getStatusCode().value() != 200) {
            throw new ProductServiceException("some error occurred while fetching product details");
        }

        return productList;
    }

    // get order amount
    public Double getOrderAmount(List<Item> itemList, List<ProductDetailResponse> productDetailResponseList)
            throws CreateOrderException {

        log.info("Get order amount");

        double orderAmount = 0.0;

        // calculating order amount
        for (Item item : itemList) {
            UUID itemId = item.getId();
            int quantity = item.getQuantity();

            Optional<ProductDetailResponse> matchingProduct = productDetailResponseList.stream()
                    .filter(productDetailResponse -> productDetailResponse.getProductId().equals(itemId))
                    .findFirst();

            if (matchingProduct.isPresent()) {
                double productPrice = matchingProduct.get().getPrice();
                orderAmount += quantity * productPrice;
            } else {
                throw new CreateOrderException("product with id = " + itemId + " not found");
            }
        }

        return orderAmount;
    }

    // get order item list
    public List<OrderItem> getOrderItemList(List<Item> itemList, List<ProductDetailResponse> productDetailResponseList)
            throws CreateOrderException {

        log.info("Get order item list");

        List<OrderItem> orderItemsList = new ArrayList<>();

        // create order item list
        for (Item item : itemList) {
            UUID itemId = item.getId();
            int quantity = item.getQuantity();

            Optional<ProductDetailResponse> matchingProduct = productDetailResponseList.stream()
                    .filter(productDetailResponse -> productDetailResponse.getProductId().equals(itemId))
                    .findFirst();

            if (matchingProduct.isPresent()) {
                orderItemsList.add(OrderItem.builder()
                        .productId(itemId)
                        .quantity(quantity)
                        .price(matchingProduct.get().getPrice())
                        .build());
            } else {
                throw new CreateOrderException("product with id = " + itemId + " not found");
            }
        }

        return orderItemsList;
    }

    // decrease product stock
    public void decreaseProductStock(String productId, Integer quantity)
            throws ProductServiceException {

        // Using FeignClient
        ResponseEntity<UUID> decreaseStockResponse;
        try {
            decreaseStockResponse = productClient.decreaseStock(productId, quantity);
        } catch (Exception e) {
            throw new ProductServiceException(e.getMessage());
        }

        if (decreaseStockResponse.getStatusCode().value() != 200) {
            throw new ProductServiceException("some error occurred while decreasing stock");
        }
    }
}
