package com.praveenukkoji.orderservice.service;

import com.praveenukkoji.orderservice.dto.request.order.CreateOrderRequest;
import com.praveenukkoji.orderservice.dto.request.order.Item;
import com.praveenukkoji.orderservice.dto.response.order.OrderResponse;
import com.praveenukkoji.orderservice.exception.order.CreateOrderException;
import com.praveenukkoji.orderservice.exception.order.OrderNotFoundException;
import com.praveenukkoji.orderservice.feign.product.model.Product;
import com.praveenukkoji.orderservice.model.Order;
import com.praveenukkoji.orderservice.model.OrderItem;
import com.praveenukkoji.orderservice.model.enums.OrderStatus;
import com.praveenukkoji.orderservice.repository.OrderRepository;
import com.praveenukkoji.orderservice.utility.OrderUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@Service
public class OrderService {

    private final OrderUtility orderUtility;
    private final OrderRepository orderRepository;

    // create
    public UUID createOrder(CreateOrderRequest createOrderRequest)
            throws CreateOrderException {

        // item list
        List<Item> itemList = createOrderRequest.getItemList();

        // item id list
        List<UUID> itemIds = itemList.stream().map(Item::getId).toList();

        // get product from product service
        // id -> isInStock
        List<Product> productList = orderUtility.getProducts(itemIds);

        // check if all products are in stock
        boolean isInStock = true;
        StringBuilder productIdsNotInStock = new StringBuilder();
        for (Product product : productList) {
            if (!product.getInStock()) {
                isInStock = false;
                productIdsNotInStock.append(product.getProductId()).append(", ");
            }
        }

        // throw exception when not in stock
        if (!isInStock) {
            throw new CreateOrderException("product's not in stock with id's = " + productIdsNotInStock);
        }

        // ----> CREATING NEW-ORDER

        // calculating total items
        int totalItems = itemList.stream()
                .mapToInt(Item::getQuantity).sum();

        // calculating order amount
        double amount = orderUtility.getOrderAmount(itemList, productList);

        // creating order
        Order newOrder = Order.builder()
                .totalItems(totalItems)
                .amount(amount)
                .status(OrderStatus.CREATED)
                .build();

        try {
            // CREATING ORDER-ITEM-LIST

            // order items list
            List<OrderItem> orderItemList = orderUtility.getOrderItemList(itemList, productList);
            orderItemList.forEach(orderItem -> orderItem.setOrder(newOrder));

            // assigning order item's to order
            newOrder.setOrderItemList(orderItemList);

            return orderRepository.save(newOrder).getId();
        } catch (Exception e) {
            throw new CreateOrderException(e.getMessage());
        }
    }

    // retrieve
    public OrderResponse getOrder(UUID orderId) throws OrderNotFoundException {

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
                    .payment(order.get().getPayment())
                    .orderItemList(order.get().getOrderItemList())
                    .build();
        }

        throw new OrderNotFoundException("order with id = " + orderId + " not found");
    }
}
