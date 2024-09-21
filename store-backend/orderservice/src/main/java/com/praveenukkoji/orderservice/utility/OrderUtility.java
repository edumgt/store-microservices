package com.praveenukkoji.orderservice.utility;

import com.praveenukkoji.orderservice.dto.request.order.Item;
import com.praveenukkoji.orderservice.exception.order.CreateOrderException;
import com.praveenukkoji.orderservice.feign.product.model.Product;
import com.praveenukkoji.orderservice.model.OrderItem;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderUtility {

    // get products
    public List<Product> getProducts(List<UUID> productIds) {
        Random random = new Random();
        boolean inStock = random.nextBoolean();

        return productIds.stream()
                .map(productId -> Product.builder()
                        .productId(productId)
                        .price(10.00)
                        .inStock(inStock)
                        .build())
                .toList();
    }

    // get order amount
    public Double getOrderAmount(List<Item> itemList, List<Product> productList)
            throws CreateOrderException {
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
                throw new CreateOrderException("product with id = " + itemId + " not found");
            }
        }

        return orderAmount;
    }

    // get order item list
    public List<OrderItem> getOrderItemList(List<Item> itemList, List<Product> productList)
            throws CreateOrderException {

        List<OrderItem> orderItemsList = new ArrayList<>();

        // create order item list
        for (Item item : itemList) {
            UUID itemId = item.getId();
            int quantity = item.getQuantity();

            Optional<Product> matchingProduct = productList.stream()
                    .filter(product -> product.getProductId().equals(itemId))
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
}
