package com.praveenukkoji.orderservice.service;

import com.praveenukkoji.orderservice.dto.request.order.CreateOrderRequest;
import com.praveenukkoji.orderservice.dto.request.order.Item;
import com.praveenukkoji.orderservice.dto.response.order.OrderResponse;
import com.praveenukkoji.orderservice.event.OrderEvent;
import com.praveenukkoji.orderservice.exception.order.CreateOrderException;
import com.praveenukkoji.orderservice.exception.order.OrderNotFoundException;
import com.praveenukkoji.orderservice.exception.order.OrderStatusUpdateException;
import com.praveenukkoji.orderservice.external.product.exception.ProductServiceException;
import com.praveenukkoji.orderservice.external.product.model.request.ProductDetailRequest;
import com.praveenukkoji.orderservice.external.product.model.response.ProductDetailResponse;
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

        List<ProductDetailRequest> productDetailRequest = itemList.stream().map(item -> ProductDetailRequest.builder()
                .productId(item.getId())
                .quantity(item.getQuantity())
                .build()
        ).toList();

        // get product from product-service
        List<ProductDetailResponse> productDetailResponseList = orderUtility.getProductDetail(productDetailRequest);

        // check if all products are in stock
        boolean isInStock = true;
        StringBuilder productIdsNotInStock = new StringBuilder();
        for (ProductDetailResponse productDetailResponse : productDetailResponseList) {
            if (!productDetailResponse.getInStock()) {
                isInStock = false;
                productIdsNotInStock.append(productDetailResponse.getProductId()).append(", ");
            }
        }

        // throw exception when not in stock
        if (!isInStock) {
            throw new CreateOrderException("product's not in stock with id's = " + productIdsNotInStock);
        }

        // ----> CREATING NEW-ORDER

        log.info("Creating order {}", createOrderRequest);

        // calculating total items
        int totalItems = itemList.stream()
                .mapToInt(Item::getQuantity).sum();

        // calculating order amount
        double amount = orderUtility.getOrderAmount(itemList, productDetailResponseList);

        // creating order
        Order newOrder = Order.builder()
                .totalItems(totalItems)
                .amount(amount)
                .status(OrderStatus.CREATED)
                .createdBy(createOrderRequest.getCreatedBy())
                .addressId(createOrderRequest.getAddressId())
                .build();

        try {

            log.info("Creating order item's");

            // CREATING ORDER-ITEM-LIST

            // order items list
            List<OrderItem> orderItemList = orderUtility.getOrderItemList(itemList, productDetailResponseList);
            orderItemList.forEach(orderItem -> orderItem.setOrder(newOrder));

            // assigning order item's to order
            newOrder.setOrderItemList(orderItemList);

            // TODO: decreasing stock call not working
            // decrease product stock
            // orderUtility.decreaseProductStock("65fd6d6b-01ab-45a7-b859-17b51a4a896a", 10);

            UUID orderId = orderRepository.save(newOrder).getId();

            // kafka sending order created notification
            OrderEvent orderEvent = OrderEvent.builder()
                    .title("Order Created")
                    .message("order created successfully with id = " + orderId)
                    .build();
            kafkaTemplate.send("orderTopic", orderEvent);

            return orderId;
        } catch (Exception e) {
            throw new CreateOrderException(e.getMessage());
        }
    }

    // retrieve
    public OrderResponse getOrder(UUID orderId) throws OrderNotFoundException {

        log.info("Get order {}", orderId);

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
    public UUID changeOrderStatus(UUID orderId, String orderStatus)
            throws OrderNotFoundException, OrderStatusUpdateException {

        log.info("Change order status {}", orderId);

        Optional<Order> order = orderRepository.findById(orderId);

        if (order.isPresent()) {
            Order updatedOrder = order.get();

            switch (orderStatus.toUpperCase()) {
                case "OUTFORDELIVERY":
                    updatedOrder.setStatus(OrderStatus.OUT_FOR_DELIVERY);
                    break;
                case "DELIVERED":
                    updatedOrder.setStatus(OrderStatus.DELIVERED);
                    break;
                case "CANCELED":
                    updatedOrder.setStatus(OrderStatus.CANCELED);
                    break;
                default:
                    throw new OrderStatusUpdateException("unknown status = " + orderStatus);
            }

            try {
                orderRepository.save(updatedOrder);

                // kafka sending order status changed notification
                OrderEvent orderEvent = OrderEvent.builder()
                        .title("Order Status Changed")
                        .message("order status changed to " + orderStatus.toUpperCase() + " for order id = " + orderId)
                        .build();
                kafkaTemplate.send("orderTopic", orderEvent);

                return orderId;
            } catch (Exception e) {
                throw new OrderStatusUpdateException(e.getMessage());
            }
        }

        throw new OrderNotFoundException("order with id = " + orderId + " not found");
    }

    // retrieve by user
    public List<OrderResponse> getOrderByUser(UUID userId) {

        log.info("Get orders by user {}", userId);

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
