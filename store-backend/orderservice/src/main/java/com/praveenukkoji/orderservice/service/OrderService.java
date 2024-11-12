package com.praveenukkoji.orderservice.service;

import com.praveenukkoji.orderservice.dto.request.order.ChangeOrderStatusRequest;
import com.praveenukkoji.orderservice.dto.request.order.CreateOrderRequest;
import com.praveenukkoji.orderservice.dto.request.order.Item;
import com.praveenukkoji.orderservice.dto.response.order.OrderResponse;
import com.praveenukkoji.orderservice.exception.order.CreateOrderException;
import com.praveenukkoji.orderservice.exception.order.OrderNotFoundException;
import com.praveenukkoji.orderservice.exception.order.OrderStatusUpdateException;
import com.praveenukkoji.orderservice.feign.dto.product.request.DecreaseProductStockRequest;
import com.praveenukkoji.orderservice.feign.dto.product.request.ProductDetailRequest;
import com.praveenukkoji.orderservice.feign.dto.product.response.ProductDetailResponse;
import com.praveenukkoji.orderservice.feign.exception.product.ProductServiceException;
import com.praveenukkoji.orderservice.kafka.order.OrderEvent;
import com.praveenukkoji.orderservice.model.Order;
import com.praveenukkoji.orderservice.model.OrderItem;
import com.praveenukkoji.orderservice.model.enums.OrderStatus;
import com.praveenukkoji.orderservice.repository.OrderRepository;
import com.praveenukkoji.orderservice.utility.OrderUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@Slf4j
@Service
public class OrderService {

    private final OrderUtility orderUtility;

    private final OrderRepository orderRepository;

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    // create
    public UUID createOrder(CreateOrderRequest createOrderRequest)
            throws CreateOrderException, ProductServiceException {

        // item list
        List<Item> itemList = createOrderRequest.getItemList();

        // creating product-detail-request dto
        List<ProductDetailRequest> productDetailRequest = itemList.stream().map(item ->
                ProductDetailRequest.builder()
                        .productId(UUID.fromString(item.getProductId()))
                        .quantity(item.getQuantity())
                        .build()
        ).toList();

        // calling product-service through feign for product-detail
        List<ProductDetailResponse> productDetailList = orderUtility.getProductDetail(productDetailRequest);

        // check if all products are in stock
        boolean isInStock = true;
        StringBuilder productIdsNotInStock = new StringBuilder();
        for (ProductDetailResponse productDetailResponse : productDetailList) {
            if (!productDetailResponse.getInStock()) {
                isInStock = false;
                productIdsNotInStock.append(productDetailResponse.getProductId()).append(", ");
            }
        }

        // when some products are not in stock throw Exception
        if (!isInStock) {
            throw new CreateOrderException("product's not in stock with id's = " + productIdsNotInStock);
        }

        // CREATING NEW-ORDER

        log.info("creating new order {}", createOrderRequest);

        // calculating total items
        int totalItems = itemList.stream()
                .mapToInt(Item::getQuantity).sum();

        // calculating order amount
        double amount = orderUtility.getOrderAmount(itemList, productDetailList);

        // TODO: verify createdBy and addressId
        Order newOrder = Order.builder()
                .totalItems(totalItems)
                .amount(amount)
                .status(OrderStatus.CREATED)
                .addressId(UUID.fromString(createOrderRequest.getAddressId()))
                .createdBy(UUID.fromString(createOrderRequest.getCreatedBy()))
                .build();

        UUID orderId = null;

        try {
            // CREATING ORDER-ITEM-LIST

            log.info("creating order item's");

            // order items list
            List<OrderItem> orderItemList = orderUtility.getOrderItemList(itemList, productDetailList);
            orderItemList.forEach(orderItem -> orderItem.setOrder(newOrder));

            // assigning order-item-list to order
            newOrder.setOrderItemList(orderItemList);

            // creating decrease-stock-request dto
            List<DecreaseProductStockRequest> decreaseProductStockRequest = itemList.stream()
                    .map(item -> DecreaseProductStockRequest.builder()
                            .productId(UUID.fromString(item.getProductId()))
                            .quantity(item.getQuantity())
                            .build()
                    ).toList();

            // calling product-service through feign to decrease product stock
            orderUtility.decreaseProductStock(decreaseProductStockRequest);

            orderId = orderRepository.save(newOrder).getId();

        } catch (Exception e) {
            throw new CreateOrderException(e.getMessage());
        }

        // kafka sending order created notification
        OrderEvent orderEvent = OrderEvent.builder()
                .title("Order Created")
                .message("order created successfully with id = " + orderId)
                .build();
        kafkaTemplate.send("orderTopic", orderEvent);

        return orderId;
    }

    // get
    public OrderResponse getOrder(UUID orderId) throws OrderNotFoundException {

        log.info("fetching order having id = {}", orderId);

        Optional<Order> order = orderRepository.findById(orderId);

        if (order.isPresent()) {
            String orderStatus = String.valueOf(order.get().getStatus());

            return OrderResponse.builder()
                    .id(order.get().getId())
                    .totalItems(order.get().getTotalItems())
                    .amount(order.get().getAmount())
                    .status(orderStatus)
                    .addressId(order.get().getAddressId())
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

    // change order status
    public UUID changeOrderStatus(ChangeOrderStatusRequest changeOrderStatusRequest)
            throws OrderNotFoundException, OrderStatusUpdateException {

        UUID orderId = UUID.fromString(changeOrderStatusRequest.getOrderId());
        String newOrderStatus = changeOrderStatusRequest.getOrderStatus();

        log.info("update order status to = {} of order having id = {}", newOrderStatus, orderId);

        Optional<Order> order = orderRepository.findById(orderId);

        if (order.isPresent()) {
            Order updatedOrder = order.get();

            switch (newOrderStatus.toUpperCase()) {
                case "OUTFORDELIVERY":
                    updatedOrder.setStatus(OrderStatus.OUT_FOR_DELIVERY);
                    break;
                case "DELIVERED":
                    updatedOrder.setStatus(OrderStatus.DELIVERED);
                    break;
                default:
                    throw new OrderStatusUpdateException("unknown order status = " + newOrderStatus);
            }

            try {
                orderRepository.save(updatedOrder);
            } catch (Exception e) {
                throw new OrderStatusUpdateException(e.getMessage());
            }

            // kafka sending order status changed notification
            OrderEvent orderEvent = OrderEvent.builder()
                    .title("Order Status Changed")
                    .message("order status changed to " + newOrderStatus.toUpperCase() + " for order id = " + orderId)
                    .build();
            kafkaTemplate.send("orderTopic", orderEvent);

            return orderId;
        }

        throw new OrderNotFoundException("order with id = " + orderId + " not found");
    }

    // get by user
    public List<OrderResponse> getOrderByUser(UUID userId) {

        log.info("fetching orders of user having id = {}", userId);

        List<Order> orders = orderRepository.findOrderByUserId(userId);

        if (orders.isEmpty()) {
            return Collections.emptyList();
        }

        return orders.stream().map(order -> {
            String orderStatus = String.valueOf(order.getStatus());

            return OrderResponse.builder()
                    .id(order.getId())
                    .totalItems(order.getTotalItems())
                    .amount(order.getAmount())
                    .status(orderStatus)
                    .addressId(order.getAddressId())
                    .createdOn(order.getCreatedOn())
                    .createdBy(order.getCreatedBy())
                    .modifiedOn(order.getModifiedOn())
                    .modifiedBy(order.getModifiedBy())
                    .payment(order.getPayment())
                    .orderItemList(order.getOrderItemList())
                    .build();
        }).toList();
    }
}
