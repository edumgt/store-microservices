package com.praveenukkoji.orderservice.controller;

import com.praveenukkoji.orderservice.dto.error.ValidationResponse;
import com.praveenukkoji.orderservice.dto.request.order.CreateOrderRequest;
import com.praveenukkoji.orderservice.exception.order.CreateOrderException;
import com.praveenukkoji.orderservice.exception.order.OrderNotFoundException;
import com.praveenukkoji.orderservice.exception.order.OrderStatusUpdateException;
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
            throws CreateOrderException {
        return ResponseEntity.status(201).body(orderService.createOrder(createOrderRequest));
    }

    // retrieve
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
            @RequestParam(defaultValue = "", name = "orderId") String orderId,
            @RequestParam(defaultValue = "", name = "orderStatus") String orderStatus
    ) throws OrderNotFoundException, OrderStatusUpdateException {
        if (Objects.equals(orderId, "")) {
            Map<String, String> error = new HashMap<>();
            error.put("orderId", "order id is empty");

            ValidationResponse response = ValidationResponse.builder()
                    .error(error)
                    .build();

            return ResponseEntity.status(400).body(response);
        }

        if (Objects.equals(orderStatus, "")) {
            Map<String, String> error = new HashMap<>();
            error.put("orderStatus", "order status is empty");

            ValidationResponse response = ValidationResponse.builder()
                    .error(error)
                    .build();

            return ResponseEntity.status(400).body(response);
        }

        UUID id = UUID.fromString(orderId);
        return ResponseEntity.status(200).body(orderService.changeOrderStatus(id, orderStatus));
    }

    // retrieve by user
    @GetMapping(path = "/get-by-user")
    public ResponseEntity<?> getOrderByUser(
            @RequestParam(defaultValue = "", name = "userId") String userId
    ) throws OrderNotFoundException {
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
