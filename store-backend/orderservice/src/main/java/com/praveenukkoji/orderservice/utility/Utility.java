package com.praveenukkoji.orderservice.utility;

import com.praveenukkoji.orderservice.dto.request.CreateOrderRequest;
import com.praveenukkoji.orderservice.dto.request.Item;
import com.praveenukkoji.orderservice.exception.product.ProductDoesNotExist;
import com.praveenukkoji.orderservice.feignClient.product.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class Utility {

    public List<Product> getProducts(List<Item> itemList) {
        // collecting id's of product
        List<UUID> productIds = itemList.stream().map(Item::getId).toList();

        // TODO: fetch list of products from product-service

        return productIds.stream()
                .map(productId -> Product.builder()
                        .productId(productId)
                        .price(10.546)
                        .quantity(10)
                        .build())
                .toList();
    }

    public Double getOrderAmount(CreateOrderRequest createOrderRequest)
            throws ProductDoesNotExist {

        List<Product> productList = getProducts(createOrderRequest.getItemList());

        double orderAmount = 0.0;

        // calculating order amount
        for (Item item : createOrderRequest.getItemList()) {
            UUID itemId = item.getId();
            int quantity = item.getQuantity();

            Optional<Product> matchingProduct = productList.stream()
                    .filter(product -> product.getProductId().equals(itemId))
                    .findFirst();

            if (matchingProduct.isPresent()) {

                double productPrice = matchingProduct.get().getPrice();
                orderAmount += quantity * productPrice;

            } else {
                throw new ProductDoesNotExist("product with id = " + itemId + " doesn't exist.");
            }
        }

        return orderAmount;
    }
}
