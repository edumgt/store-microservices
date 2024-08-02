package com.praveenukkoji.orderservice.service;

import com.praveenukkoji.orderservice.dto.request.CreateOrderRequest;
import com.praveenukkoji.orderservice.dto.request.Item;
import com.praveenukkoji.orderservice.dto.response.OrderResponse;
import com.praveenukkoji.orderservice.exception.CreateOrderException;
import com.praveenukkoji.orderservice.exception.OrderNotFoundException;
import com.praveenukkoji.orderservice.exception.ProductDoesNotExist;
import com.praveenukkoji.orderservice.exception.QuantityNotAvailable;
import com.praveenukkoji.orderservice.feign.product.model.Product;
import com.praveenukkoji.orderservice.model.Order;
import com.praveenukkoji.orderservice.model.OrderItem;
import com.praveenukkoji.orderservice.model.Payment;
import com.praveenukkoji.orderservice.model.types.OrderStatus;
import com.praveenukkoji.orderservice.model.types.PaymentStatus;
import com.praveenukkoji.orderservice.repository.OrderRepository;
import com.praveenukkoji.orderservice.utility.Utility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private Utility utility;

    public Object createOrder(CreateOrderRequest createOrderRequest)
            throws CreateOrderException, QuantityNotAvailable, ProductDoesNotExist {

        int orderItemCount = createOrderRequest.getItemList().stream()
                .mapToInt(Item::getQuantity).sum();

        List<Product> productList = utility.getProductList(createOrderRequest);

        double orderAmount = utility.getOrderAmount(createOrderRequest, productList);

        // TODO: reduce the value of quantity of product in
        //  product table here because we know that quantity
        //  is available for all items that we came here to make payment

        // creating payment
        Payment payment = Payment.builder()
                .paymentAmount(orderAmount)
                .paymentStatus(PaymentStatus.SUCCESS)
                .createdOn(LocalDateTime.now())
                .createdBy(createOrderRequest.getCreatedBy())
                .build();

        // TODO: if payment not SUCCESS then do not place order

        // creating order
        Order newOrder = Order.builder()
                .orderItemCount(orderItemCount)
                .orderAmount(orderAmount)
                .orderStatus(OrderStatus.PLACED)
                .createdOn(LocalDateTime.now())
                .createdBy(createOrderRequest.getCreatedBy())
                .payment(payment)
                .build();

        // creating list of order items list
        // TODO: set productPrice according to fetched productList from product table
        List<OrderItem> orderItemList = new ArrayList<>();

        List<Item> itemList = createOrderRequest.getItemList();

        for (Product product : productList) {
            UUID productId = product.getProductId();
            double price = product.getPrice();

            Optional<Item> matchingItem = itemList.stream()
                    .filter(item -> item.getProductId().equals(productId))
                    .findFirst();

            if (matchingItem.isPresent()) {
                orderItemList.add(OrderItem.builder()
                        .productId(matchingItem.get().getProductId())
                        .productQuantity(matchingItem.get().getQuantity())
                        .productPrice(price)
                        .order(newOrder)
                        .build()
                );
            } else {
                log.error("Some error occurred while creating order item list");
            }
        }

        newOrder.setOrderItemList(orderItemList);

        try {
            Order order = orderRepository.saveAndFlush(newOrder);
            return order.getOrderId();

        } catch (Exception e) {
            throw new CreateOrderException();
        }
    }

    public OrderResponse getOrder(UUID orderId)
            throws OrderNotFoundException {
        Optional<Order> order = orderRepository.findById(orderId);

        if (order.isPresent()) {
            return OrderResponse.builder()
                    .orderId(order.get().getOrderId())
                    .orderItemList(order.get().getOrderItemList())
                    .payment(order.get().getPayment())
                    .orderItemCount(order.get().getOrderItemCount())
                    .orderAmount(order.get().getOrderAmount())
                    .orderStatus(order.get().getOrderStatus())
                    .createdOn(order.get().getCreatedOn())
                    .createdBy(order.get().getCreatedBy())
                    .modifiedOn(order.get().getModifiedOn())
                    .build();
        } else {
            throw new OrderNotFoundException();
        }
    }
}
