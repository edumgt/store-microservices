package com.praveenukkoji.orderservice.utility;

import com.praveenukkoji.orderservice.dto.request.CreateOrderRequest;
import com.praveenukkoji.orderservice.dto.request.Item;
import com.praveenukkoji.orderservice.exception.ProductDoesNotExist;
import com.praveenukkoji.orderservice.exception.QuantityNotAvailable;
import com.praveenukkoji.orderservice.feign.product.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class Utility {

    public List<Product> getProductList(CreateOrderRequest createOrderRequest) {
        // collecting id's of product to fetch product from product-service
        List<UUID> productIds = createOrderRequest.getItemList()
                .stream().map(Item::getProductId).toList();

        // fetching list of product from product-service
        return productIds.stream()
                .map(productId -> Product.builder()
                        .productId(productId)
                        .price(10.0)
                        .quantity(10)
                        .build())
                .toList();
    }

    public Double getOrderAmount(CreateOrderRequest createOrderRequest, List<Product> productList)
            throws QuantityNotAvailable, ProductDoesNotExist {

        double orderAmount = 0.0;

        // calculating order amount
        for (Item item : createOrderRequest.getItemList()) {
            UUID itemId = item.getProductId();
            Integer quantity = item.getQuantity();

            Optional<Product> matchingProduct = productList.stream()
                    .filter(product -> product.getProductId().equals(itemId))
                    .findFirst();

            if (matchingProduct.isPresent()) {
                if (matchingProduct.get().getQuantity() < quantity) {
                    throw new QuantityNotAvailable(matchingProduct.get().getQuantity() +
                            " quantity available for product with id = " + itemId);
                } else {
                    double productPrice = matchingProduct.get().getPrice();
                    orderAmount += quantity * productPrice;
                }
            } else {
                throw new ProductDoesNotExist("product with id = " + itemId + " doesn't exist.");
            }
        }

        return orderAmount;
    }
}
