package com.praveenukkoji.orderservice.utility;

import com.praveenukkoji.orderservice.dto.request.order.Item;
import com.praveenukkoji.orderservice.exception.product.ProductNotFoundException;
import com.praveenukkoji.orderservice.feign.product.model.Product;
import com.praveenukkoji.orderservice.model.OrderItem;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderUtility {

    // get products
    public List<Product> getProducts(List<UUID> productIds) {
        return productIds.stream()
                .map(productId -> Product.builder()
                        .productId(productId)
                        .price(10.00)
                        .inStock(true)
                        .build())
                .toList();
    }

    // get order amount
    public Double getOrderAmount(List<Item> itemList, List<Product> productList)
            throws ProductNotFoundException {
        double orderAmount = 0.0;

        // calculating order amount
        for (Item item : itemList) {
            UUID itemId = item.getId();
            int quantity = item.getQuantity();

            Optional<Product> matchingProduct = productList.stream()
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

    // get order-item-list
    public List<OrderItem> getOrderItemList(List<Item> itemList, List<Product> productList) {
        return Collections.emptyList();
    }
}
