package com.praveenukkoji.orderservice.service;

import com.praveenukkoji.orderservice.dto.request.CreateOrderRequest;
import com.praveenukkoji.orderservice.dto.request.Product;
import com.praveenukkoji.orderservice.dto.response.GetOrderResponse;
import com.praveenukkoji.orderservice.exception.CreateOrderException;
import com.praveenukkoji.orderservice.exception.OrderNotFoundException;
import com.praveenukkoji.orderservice.model.Order;
import com.praveenukkoji.orderservice.model.OrderItem;
import com.praveenukkoji.orderservice.repository.OrderItemRepository;
import com.praveenukkoji.orderservice.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public UUID createOrder(CreateOrderRequest createOrderRequest)
            throws CreateOrderException {
        Integer totalItems = createOrderRequest.getProducts().stream()
                .mapToInt(Product::getProductQty).sum();

        // TODO: fetch and calculate the amount from product service
        Double totalAmount = 0.0;

        Order newOrder = Order.builder()
                .orderId(UUID.randomUUID())
                .totalItems(totalItems)
                .totalAmount(totalAmount)
                .orderStatus("PROCESSING")
                .createdOn(LocalDate.now())
                .createdBy(createOrderRequest.getCreatedBy())
                .build();

        List<OrderItem> orderItem_entity = createOrderRequest.getProducts().stream()
                .map(product -> OrderItem.builder()
                        .orderItemId(UUID.randomUUID())
                        .productId(product.getProductId())
                        .productQty(product.getProductQty())
                        .order(newOrder)
                        .build()
                ).toList();

        newOrder.setOrderItems(orderItem_entity);

        try {
            Order queryResult = orderRepository.saveAndFlush(newOrder);

            // TODO: reduce the value of quantity of product in product db

            log.info("order created with id = {} and processing", queryResult.getOrderId());

            return queryResult.getOrderId();

        } catch (Exception e) {
            throw new CreateOrderException("unable to create order");
        }
    }

    public GetOrderResponse getOrder(UUID orderId)
            throws OrderNotFoundException {
        Optional<Order> order = orderRepository.findById(orderId);

        if (order.isPresent()) {
            log.info("order fetched with order id = {}", orderId);

            List<OrderItem> orderItems = order.get().getOrderItems();

            List<Product> products = orderItems.stream().map(ele ->
                    Product.builder()
                            .productId(ele.getProductId())
                            .productQty(ele.getProductQty())
                            .build()
            ).toList();

            return GetOrderResponse.builder()
                    .orderId(orderId)
                    .products(products)
                    .totalItems(order.get().getTotalItems())
                    .totalAmount(order.get().getTotalAmount())
                    .orderStatus(order.get().getOrderStatus())
                    .createdOn(order.get().getCreatedOn())
                    .createdBy(order.get().getCreatedBy())
                    .build();
        } else {
            throw new OrderNotFoundException("order not found");
        }
    }
}
