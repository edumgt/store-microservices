package com.praveenukkoji.orderservice.service;

import com.praveenukkoji.orderservice.dto.OrderCreateRequest;
import com.praveenukkoji.orderservice.dto.OrderDetail;
import com.praveenukkoji.orderservice.dto.Product;
import com.praveenukkoji.orderservice.exception.OrderDoesNotExistException;
import com.praveenukkoji.orderservice.model.Order;
import com.praveenukkoji.orderservice.model.OrderItem;
import com.praveenukkoji.orderservice.repository.OrderItemRepository;
import com.praveenukkoji.orderservice.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public UUID createOrder(OrderCreateRequest orderCreateRequest) {

        Integer total_items = orderCreateRequest.getProducts().stream().mapToInt(Product::getProduct_qty).sum();

        Double total_amount = orderCreateRequest.getProducts().stream()
                .mapToDouble(ele -> ele.getProduct_qty() * ele.getProduct_price()).sum();

        Order entity = Order.builder()
                .total_items(total_items)
                .total_amount(total_amount)
                .created_on(LocalDate.now())
                .created_by(orderCreateRequest.getCreated_by())
                .build();

        Order queryResult = orderRepository.saveAndFlush(entity);
        log.info("create_order - order created successfully");

        List<OrderItem> entityOI = orderCreateRequest.getProducts().stream()
                .map(ele -> OrderItem.builder()
                        .order_id(queryResult.getOrder_id())
                        .product_id(ele.getProduct_id())
                        .product_price(ele.getProduct_price())
                        .product_qty(ele.getProduct_qty())
                        .build()
                ).toList();

        orderItemRepository.saveAll(entityOI);
        log.info("create_order - order items saved for order id = {}", queryResult.getOrder_id());

        return queryResult.getOrder_id();
    }

    public OrderDetail getOrder(UUID order_id)
            throws OrderDoesNotExistException {

        Optional<Order> entity = orderRepository.findById(order_id);

        if (entity.isPresent()) {
            log.info("get_order - order fetched with order id = {}", order_id);

            List<OrderItem> queryResult = orderItemRepository.findAllById(order_id);

            List<Product> products = queryResult.stream().map(ele ->
                    Product.builder()
                            .product_id(ele.getProduct_id())
                            .product_price(ele.getProduct_price())
                            .product_qty(ele.getProduct_qty())
                            .build()
            ).toList();
            log.info("get_order - order items fetched with order id = {}", order_id);

            return OrderDetail.builder()
                    .orderId(order_id)
                    .product_list(products)
                    .total_items(entity.get().getTotal_items())
                    .total_amount(entity.get().getTotal_amount())
                    .created_on(entity.get().getCreated_on())
                    .created_by(entity.get().getCreated_by())
                    .build();
        } else {
            throw new OrderDoesNotExistException("get_order - order does not exist");
        }
    }
}
