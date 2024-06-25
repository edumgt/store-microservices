package com.praveenukkoji.orderservice.service;

import com.praveenukkoji.orderservice.dto.extra.Product;
import com.praveenukkoji.orderservice.dto.request.CreateOrderRequest;
import com.praveenukkoji.orderservice.dto.response.GetOrderResponse;
import com.praveenukkoji.orderservice.dto.response.OrderResponse;
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

@Service
@Slf4j
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public OrderResponse createOrder(CreateOrderRequest createOrderRequest)
            throws CreateOrderException {

        Integer total_items = createOrderRequest.getProducts().stream().mapToInt(Product::getProduct_qty).sum();

//        Double total_amount = 0.0;
        Double total_amount = createOrderRequest.getProducts().stream()
                .mapToDouble(ele -> ele.getProduct_qty() * ele.getProduct_price()).sum();

        Order order_entity = Order.builder()
                .total_items(total_items)
                .total_amount(total_amount)
                .order_status("PROCESSING")
                .created_on(LocalDate.now())
                .created_by(createOrderRequest.getCreated_by())
                .build();

        UUID order_id = null;

        try {
            Order queryResult = orderRepository.saveAndFlush(order_entity);

            order_id = queryResult.getOrder_id();

            log.info("order created with id = {} and in processing", queryResult.getOrder_id());

            List<OrderItem> orderItem_entity = createOrderRequest.getProducts().stream()
                    .map(ele -> OrderItem.builder()
                            .order_id(queryResult.getOrder_id())
                            .product_id(ele.getProduct_id())
                            .product_price(ele.getProduct_price())
                            .product_qty(ele.getProduct_qty())
                            .build()
                    ).toList();

            orderItemRepository.saveAll(orderItem_entity);

            log.info("order items saved for order id = {}", order_id);

            return OrderResponse.builder()
                    .message("order created with id = " + queryResult.getOrder_id() + " and in processing")
                    .build();

        } catch (Exception e) {
            if (order_id != null) {
                orderRepository.deleteById(order_id);
            }

            throw new CreateOrderException("unable to create order");
        }
    }

    public GetOrderResponse getOrder(UUID order_id)
            throws OrderNotFoundException {

        Optional<Order> entity = orderRepository.findById(order_id);

        if (entity.isPresent()) {
            log.info("order fetched with order id = {}", order_id);

            List<OrderItem> orderItemQueryResult = orderItemRepository.findAllById(order_id);

            List<Product> products = orderItemQueryResult.stream().map(ele ->
                    Product.builder()
                            .product_id(ele.getProduct_id())
                            .product_price(ele.getProduct_price())
                            .product_qty(ele.getProduct_qty())
                            .build()
            ).toList();

            log.info("order items fetched of order id = {}", order_id);

            return GetOrderResponse.builder()
                    .orderId(order_id)
                    .product_list(products)
                    .total_items(entity.get().getTotal_items())
                    .total_amount(entity.get().getTotal_amount())
                    .order_status(entity.get().getOrder_status())
                    .created_on(entity.get().getCreated_on())
                    .created_by(entity.get().getCreated_by())
                    .build();
        } else {
            throw new OrderNotFoundException("order not found");
        }
    }
}
