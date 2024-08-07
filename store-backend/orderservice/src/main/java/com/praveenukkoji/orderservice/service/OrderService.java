package com.praveenukkoji.orderservice.service;

import com.praveenukkoji.orderservice.dto.request.CreateOrderRequest;
import com.praveenukkoji.orderservice.dto.request.Item;
import com.praveenukkoji.orderservice.dto.response.OrderResponse;
import com.praveenukkoji.orderservice.exception.order.CreateOrderException;
import com.praveenukkoji.orderservice.exception.order.OrderNotFoundException;
import com.praveenukkoji.orderservice.exception.payment.PaymentNotFoundException;
import com.praveenukkoji.orderservice.exception.product.ProductDoesNotExist;
import com.praveenukkoji.orderservice.exception.product.QuantityNotAvailable;
import com.praveenukkoji.orderservice.feignClient.product.model.Product;
import com.praveenukkoji.orderservice.model.Order;
import com.praveenukkoji.orderservice.model.OrderItem;
import com.praveenukkoji.orderservice.model.Payment;
import com.praveenukkoji.orderservice.repository.OrderRepository;
import com.praveenukkoji.orderservice.repository.PaymentRepository;
import com.praveenukkoji.orderservice.utility.Utility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private Utility utility;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Transactional
    public UUID createOrder(CreateOrderRequest createOrderRequest)
            throws CreateOrderException, QuantityNotAvailable, ProductDoesNotExist {

        // calculating total items count
        int totalItems = createOrderRequest.getItemList().stream()
                .mapToInt(Item::getQuantity).sum();

        // calculating order amount
        double orderAmount = utility.getOrderAmount(createOrderRequest);

        // creating order
        Order newOrder = Order.builder()
                .totalItems(totalItems)
                .amount(orderAmount)
                .status("CREATED")
                .build();

        // creating list of order items list
        List<OrderItem> orderItemList = new ArrayList<>();

        List<Item> itemList = createOrderRequest.getItemList();

        // getting item from product service
        List<Product> productList = utility.getProducts(itemList);


        for (Item item : itemList) {
            UUID itemId = item.getId();
            int itemQty = item.getQuantity();

            Optional<Product> matchingProduct = productList.stream()
                    .filter(product -> product.getProductId().equals(itemId))
                    .findFirst();

            if (matchingProduct.isPresent()) {
                if (matchingProduct.get().getQuantity() >= itemQty) {
                    orderItemList.add(
                            OrderItem.builder()
                                    .productId(itemId)
                                    .quantity(itemQty)
                                    .price(matchingProduct.get().getPrice())
                                    .order(newOrder)
                                    .build()
                    );
                } else {
                    throw new QuantityNotAvailable("quantity not available for product id = " + itemId);
                }
            } else {
                throw new ProductDoesNotExist("product with id = " + itemId + " doesn't exist.");
            }
        }

        newOrder.setOrderItemList(orderItemList);

        try {
            // TODO: update quantity of products threw product-service

            return orderRepository.save(newOrder).getId();
        } catch (Exception e) {
            throw new CreateOrderException();
        }
    }

    public OrderResponse getOrder(UUID orderId)
            throws OrderNotFoundException, PaymentNotFoundException {

        Optional<Order> order = orderRepository.findById(orderId);

        if (order.isPresent()) {

            OrderResponse response = OrderResponse.builder()
                    .id(order.get().getId())
                    .totalItems(order.get().getTotalItems())
                    .amount(order.get().getAmount())
                    .status(order.get().getStatus())
                    .createdOn(order.get().getCreatedOn())
                    .createdBy(order.get().getCreatedBy())
                    .modifiedOn(order.get().getModifiedOn())
                    .modifiedBy(order.get().getModifiedBy())
                    .orderItemList(order.get().getOrderItemList())
                    .build();

            if (order.get().getPayment() != null) {

                UUID paymentId = order.get().getPayment().getId();
                Optional<Payment> payment = paymentRepository.findById(paymentId);

                if (payment.isPresent()) {
                    response.setPayment(payment.get());
                } else {
                    throw new PaymentNotFoundException();
                }
            }

            return response;

        } else {
            throw new OrderNotFoundException();
        }
    }
}
