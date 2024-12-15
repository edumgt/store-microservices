package com.praveenukkoji.orderservice.service;

import com.praveenukkoji.orderservice.dto.order.request.ChangeOrderStatusRequest;
import com.praveenukkoji.orderservice.dto.order.request.CreateOrderRequest;
import com.praveenukkoji.orderservice.dto.order.request.Item;
import com.praveenukkoji.orderservice.dto.order.response.OrderResponse;
import com.praveenukkoji.orderservice.exception.error.ValidationException;
import com.praveenukkoji.orderservice.exception.order.CreateOrderException;
import com.praveenukkoji.orderservice.exception.order.OrderNotFoundException;
import com.praveenukkoji.orderservice.exception.order.OrderStatusUpdateException;
import com.praveenukkoji.orderservice.exception.order.ProductNotFoundException;
import com.praveenukkoji.orderservice.feign.dto.product.response.ProductDetailResponse;
import com.praveenukkoji.orderservice.feign.exception.product.ProductServiceException;
import com.praveenukkoji.orderservice.model.Order;
import com.praveenukkoji.orderservice.model.OrderItem;
import com.praveenukkoji.orderservice.model.OrderStatus;
import com.praveenukkoji.orderservice.model.PaymentStatus;
import com.praveenukkoji.orderservice.repository.OrderRepository;
import com.praveenukkoji.orderservice.utility.OrderUtility;
import io.github.praveenukkoji.events.OrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Transactional
@Slf4j
@Service
public class OrderService {

    private final OrderUtility orderUtility;

    private final OrderRepository orderRepository;

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @Value("${spring.kafka.topic}")
    private String orderTopic;

    // TODO: verify userId and addressId
    // create
    public UUID createOrder(CreateOrderRequest createOrderRequest)
            throws CreateOrderException, ProductServiceException, ProductNotFoundException, ValidationException {

        log.info("creating new order");

        // item list
        List<Item> itemList = createOrderRequest.getItemList();

        if(itemList.isEmpty()) {
            throw new ValidationException("itemList", "item list cannot be empty");
        }

        if(Objects.equals(createOrderRequest.getUserId(), "")) {
            throw new ValidationException("userId", "user id cannot be empty");
        }

        if(Objects.equals(createOrderRequest.getAddressId(), "")) {
            throw new ValidationException("addressId", "addressId cannot be empty");
        }

        // calling product-service through feign for product-detail
        List<ProductDetailResponse> productDetailList = orderUtility.getProductDetail(itemList);

        // check if all products are in stock
        boolean isInStock = true;
        StringBuilder productIdsNotInStock = new StringBuilder();
        for (ProductDetailResponse productDetailResponse : productDetailList) {
            if (!productDetailResponse.getInStock()) {
                isInStock = false;
                productIdsNotInStock.append(productDetailResponse.getProductId()).append(", ");
            }
        }

        // if any product is not in stock, throw Exception
        if (!isInStock) {
            throw new CreateOrderException("product with id not in stock, ID's = " + productIdsNotInStock);
        }

        // creating new order

        // calculating total items
        int totalItems = itemList.stream()
                .mapToInt(Item::getQuantity).sum();

        // calculating order amount
        double amount = orderUtility.getOrderAmount(itemList, productDetailList);

        Order newOrder = Order.builder()
                .totalItems(totalItems)
                .amount(amount)
                .orderStatus(OrderStatus.CREATED.getValue())
                .paymentStatus(PaymentStatus.UN_PAID.getValue())
                .addressId(createOrderRequest.getAddressId())
                .userId(createOrderRequest.getUserId())
                .build();

        UUID orderId = null;

        try {
            // creating order items
            log.info("creating order items");

            // order items list
            List<OrderItem> orderItemList = orderUtility.getOrderItemList(itemList, productDetailList);

            // assigning order to each order item
            orderItemList.forEach(orderItem -> orderItem.setOrder(newOrder));

            // assigning order-item-list to order
            newOrder.setOrderItemList(orderItemList);

            // calling product-service through feign to decrease product stock
            orderUtility.decreaseProductStock(itemList);

            // saving new order
            orderId = orderRepository.save(newOrder).getId();
        }
        catch (ProductServiceException e) {
            throw new ProductServiceException(e.getMessage());
        }
        catch (ProductNotFoundException e) {
            throw new ProductNotFoundException(e.getMessage());
        }
        catch (Exception e) {
            throw new CreateOrderException(e.getMessage());
        }

        // kafka sending order created notification
        OrderEvent orderEvent = OrderEvent.builder()
                .orderId(String.valueOf(orderId))
                .status(OrderStatus.CREATED.getValue())
                .message("order created")
                .build();
        kafkaTemplate.send(orderTopic, orderEvent);

        return orderId;
    }

    // get
    public OrderResponse getOrder(String id) throws OrderNotFoundException, ValidationException {

        log.info("fetching order having id = {}", id);

        if(Objects.equals(id, "")) {
            throw new ValidationException("orderId", "order id cannot be empty");
        }

        UUID orderId = UUID.fromString(id);

        Optional<Order> order = orderRepository.findById(orderId);

        if (order.isPresent()) {
            return OrderResponse.builder()
                    .id(order.get().getId())
                    .totalItems(order.get().getTotalItems())
                    .amount(order.get().getAmount())
                    .orderStatus(order.get().getOrderStatus())
                    .paymentStatus(order.get().getPaymentStatus())
                    .addressId(order.get().getAddressId())
                    .userId(order.get().getUserId())
                    .createdOn(order.get().getCreatedOn())
                    .createdBy(order.get().getCreatedBy())
                    .modifiedOn(order.get().getModifiedOn())
                    .modifiedBy(order.get().getModifiedBy())
                    .orderItemList(order.get().getOrderItemList())
                    .build();
        }

        throw new OrderNotFoundException("order with id = " + orderId + " not found");
    }

    // change order status
    public void changeOrderStatus(ChangeOrderStatusRequest changeOrderStatusRequest)
            throws OrderNotFoundException, OrderStatusUpdateException, ValidationException {

        String id = changeOrderStatusRequest.getOrderId();
        String newOrderStatus = changeOrderStatusRequest.getOrderStatus().toLowerCase();

        log.info("update order status to = {} of order having id = {}", newOrderStatus, id);

        if(Objects.equals(id, "")) {
            throw new ValidationException("orderId", "order id cannot be empty");
        }

        if(Objects.equals(newOrderStatus, "")) {
            throw new ValidationException("orderStatus", "orderStatus cannot be empty");
        }

        ArrayList<String> orderStatusTypeList = new ArrayList<>(
                Arrays.asList("created", "canceled", "placed", "out-for-delivery", "delivered"));

        if(!orderStatusTypeList.contains(newOrderStatus)) {
            throw new ValidationException("orderStatus", "invalid order status = " + newOrderStatus);
        }

        try {
            UUID orderId = UUID.fromString(id);
            Optional<Order> order = orderRepository.findById(orderId);

            if (order.isPresent()) {
                Order updatedOrder = order.get();
                updatedOrder.setOrderStatus(newOrderStatus);

                orderRepository.save(updatedOrder);
            }
            else {
                throw new OrderNotFoundException("order with id = " + id + " not found");
            }
        }
        catch (OrderNotFoundException e) {
            throw new OrderNotFoundException(e.getMessage());
        }
        catch (Exception e) {
            throw new OrderStatusUpdateException(e.getMessage());
        }

        // kafka sending order status changed notification
        OrderEvent orderEvent = OrderEvent.builder()
                .orderId(id)
                .status(newOrderStatus)
                .message("order status changed")
                .build();
        kafkaTemplate.send(orderTopic, orderEvent);
    }

    // get by user
    public List<OrderResponse> getOrderByUser(String userId) throws ValidationException {

        log.info("fetching orders of user having id = {}", userId);

        if(Objects.equals(userId, "")) {
            throw new ValidationException("userId", "user id cannot be empty");
        }

        List<Order> orders = orderRepository.findOrderByUserId(userId);

        if (orders.isEmpty()) {
            return Collections.emptyList();
        }

        return orders.stream().map(order ->
                OrderResponse.builder()
                .id(order.getId())
                .totalItems(order.getTotalItems())
                .amount(order.getAmount())
                .orderStatus(order.getOrderStatus())
                .paymentStatus(order.getPaymentStatus())
                .addressId(order.getAddressId())
                .userId(order.getUserId())
                .createdOn(order.getCreatedOn())
                .createdBy(order.getCreatedBy())
                .modifiedOn(order.getModifiedOn())
                .modifiedBy(order.getModifiedBy())
                .orderItemList(order.getOrderItemList())
                .build()
        ).toList();
    }
}
