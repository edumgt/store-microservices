package com.praveenukkoji.orderservice.controller;

import com.praveenukkoji.orderservice.dto.error.ValidationResponse;
import com.praveenukkoji.orderservice.dto.request.order.ChangeOrderStatusRequest;
import com.praveenukkoji.orderservice.dto.request.order.CreateOrderRequest;
import com.praveenukkoji.orderservice.exception.order.CreateOrderException;
import com.praveenukkoji.orderservice.exception.order.OrderNotFoundException;
import com.praveenukkoji.orderservice.exception.order.OrderStatusUpdateException;
import com.praveenukkoji.orderservice.feign.exception.product.ProductServiceException;
import com.praveenukkoji.orderservice.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    // create
    @PostMapping(path = "")
    public ResponseEntity<?> createOrder(@RequestBody @Valid CreateOrderRequest createOrderRequest)
            throws CreateOrderException, ProductServiceException {
        return ResponseEntity.status(201).body(orderService.createOrder(createOrderRequest));
    }

    // get
    @GetMapping(path = "")
    public ResponseEntity<?> getOrder(
            @RequestParam(defaultValue = "", name = "orderId") String orderId
    ) throws OrderNotFoundException {
        if (Objects.equals(orderId, "")) {
            Map<String, String> error = new HashMap<>();
            error.put("orderId", "order id is empty");

            ValidationResponse response = ValidationResponse.builder()
                    .error(error)
                    .build();

            return ResponseEntity.status(400).body(response);
        }

        UUID id = UUID.fromString(orderId);
        return ResponseEntity.status(200).body(orderService.getOrder(id));
    }

    // update order status
    @PatchMapping("/order-status")
    public ResponseEntity<?> changeOrderStatus(
            @RequestBody @Valid ChangeOrderStatusRequest changeOrderStatusRequest
    ) throws OrderNotFoundException, OrderStatusUpdateException {
        return ResponseEntity.status(200).body(orderService.changeOrderStatus(changeOrderStatusRequest));
    }

    // get by user
    @GetMapping(path = "/get-by-user")
    public ResponseEntity<?> getOrderByUser(
            @RequestParam(defaultValue = "", name = "userId") String userId
    ) {
        if (Objects.equals(userId, "")) {
            Map<String, String> error = new HashMap<>();
            error.put("userId", "user id is empty");

            ValidationResponse response = ValidationResponse.builder()
                    .error(error)
                    .build();

            return ResponseEntity.status(400).body(response);
        }

        UUID id = UUID.fromString(userId);
        return ResponseEntity.status(200).body(orderService.getOrderByUser(id));
    }
}
