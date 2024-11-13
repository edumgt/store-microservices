package com.praveenukkoji.orderservice.utility;

import com.praveenukkoji.orderservice.dto.request.order.Item;
import com.praveenukkoji.orderservice.exception.order.CreateOrderException;
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
    public List<ProductDetailResponse> getProductDetail(List<ProductDetailRequest> productDetailRequest)
            throws ProductServiceException {

        log.info("fetching product detail from product-service");

        if (productDetailRequest.isEmpty()) {
            throw new ProductServiceException("product detail request is empty");
        }

        try {
            // Using FeignClient
            ResponseEntity<List<ProductDetailResponse>> productDetailsResponse = productClient.getProductDetails(productDetailRequest);

            if (productDetailsResponse.getStatusCode().value() != 200) {
                throw new ProductServiceException("some error occurred while fetching product details");
            }

            return productDetailsResponse.getBody();
        } catch (Exception e) {
            throw new ProductServiceException(e.getMessage());
        }
    }

    // get order amount
    public Double getOrderAmount(List<Item> itemList, List<ProductDetailResponse> productDetailList)
            throws CreateOrderException {

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
                throw new CreateOrderException("product with id = " + itemId + " not found");
            }
        }

        return orderAmount;
    }

    // get order item list
    public List<OrderItem> getOrderItemList(List<Item> itemList, List<ProductDetailResponse> productDetailList)
            throws CreateOrderException {

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
                throw new CreateOrderException("product with id = " + itemId + " not found");
            }
        }

        return orderItemsList;
    }

    // decreasing product stock through product-service
    public void decreaseProductStock(List<DecreaseProductStockRequest> decreaseProductStockRequest)
            throws ProductServiceException {
        
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
