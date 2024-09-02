package com.praveenukkoji.orderservice.service;

import com.praveenukkoji.orderservice.dto.request.order.CreateOrderRequest;
import com.praveenukkoji.orderservice.dto.request.order.Item;
import com.praveenukkoji.orderservice.dto.response.order.OrderResponse;
import com.praveenukkoji.orderservice.exception.order.CreateOrderException;
import com.praveenukkoji.orderservice.exception.order.OrderNotFoundException;
import com.praveenukkoji.orderservice.exception.product.ProductNotFoundException;
import com.praveenukkoji.orderservice.exception.product.ProductNotInStock;
import com.praveenukkoji.orderservice.feign.product.model.Product;
import com.praveenukkoji.orderservice.model.Order;
import com.praveenukkoji.orderservice.model.OrderItem;
import com.praveenukkoji.orderservice.model.enums.OrderStatus;
import com.praveenukkoji.orderservice.repository.OrderRepository;
import com.praveenukkoji.orderservice.utility.OrderUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class OrderService {

    private final OrderUtility orderUtility;
    private final OrderRepository orderRepository;

    public UUID createOrder(CreateOrderRequest createOrderRequest)
            throws CreateOrderException, ProductNotInStock, ProductNotFoundException {

        List<Item> itemList = createOrderRequest.getItemList();
        List<UUID> itemIds = itemList.stream().map(Item::getId).toList();

        // getting product's from product-service
        // TODO:
        List<Product> productList = orderUtility.getProducts(itemIds);

        // validating if all product in stock
        for (Product product : productList) {
            if (!product.getInStock()) {
                throw new ProductNotInStock("product with id = " + product.getProductId() + " is not in stock");
            }
        }

        // CREATING NEW-ORDER

        // calculating total-items
        int totalItems = itemList.stream()
                .mapToInt(Item::getQuantity).sum();

        // calculating order amount
        // TODO:
        double amount = orderUtility.getOrderAmount(itemList, productList);

        // creating order
        Order newOrder = Order.builder()
                .totalItems(totalItems)
                .amount(amount)
                .status(OrderStatus.CREATED)
                .build();

        // CREATING ORDER-ITEM-LIST

        //TODO:
        List<OrderItem> orderItemList = orderUtility.getOrderItemList(itemList, productList);

        newOrder.setOrderItemList(orderItemList);

        try {
            return orderRepository.save(newOrder).getId();
        } catch (Exception e) {
            throw new CreateOrderException();
        }
    }

    public OrderResponse getOrder(UUID orderId)
            throws OrderNotFoundException {

        Optional<Order> order = orderRepository.findById(orderId);

        if (order.isPresent()) {
            String orderStatus = String.valueOf(order.get().getStatus());

            return OrderResponse.builder()
                    .id(order.get().getId())
                    .totalItems(order.get().getTotalItems())
                    .amount(order.get().getAmount())
                    .status(orderStatus)
                    .createdOn(order.get().getCreatedOn())
                    .createdBy(order.get().getCreatedBy())
                    .modifiedOn(order.get().getModifiedOn())
                    .modifiedBy(order.get().getModifiedBy())
                    .orderItemList(order.get().getOrderItemList())
                    .payment(order.get().getPayment())
                    .build();
        }

        throw new OrderNotFoundException();
    }
}
