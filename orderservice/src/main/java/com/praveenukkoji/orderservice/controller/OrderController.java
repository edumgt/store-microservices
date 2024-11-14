package com.praveenukkoji.orderservice.controller;

import com.praveenukkoji.orderservice.dto.order.request.ChangeOrderStatusRequest;
import com.praveenukkoji.orderservice.dto.order.request.CreateOrderRequest;
import com.praveenukkoji.orderservice.exception.error.ValidationException;
import com.praveenukkoji.orderservice.exception.order.CreateOrderException;
import com.praveenukkoji.orderservice.exception.order.OrderNotFoundException;
import com.praveenukkoji.orderservice.exception.order.OrderStatusUpdateException;
import com.praveenukkoji.orderservice.exception.order.ProductNotFoundException;
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
    public ResponseEntity<?> createOrder(@RequestBody @Valid CreateOrderRequest createOrderRequest
    ) throws CreateOrderException, ProductServiceException, ProductNotFoundException, ValidationException {
        return ResponseEntity.status(201).body(orderService.createOrder(createOrderRequest));
    }

    // get
    @GetMapping(path = "")
    public ResponseEntity<?> getOrder(
            @RequestParam(defaultValue = "", name = "orderId") String orderId
    ) throws OrderNotFoundException, ValidationException {
        return ResponseEntity.status(200).body(orderService.getOrder(orderId));
    }

    // update order status
    @PatchMapping("/order-status")
    public ResponseEntity<?> changeOrderStatus(
            @RequestBody @Valid ChangeOrderStatusRequest changeOrderStatusRequest
    ) throws OrderNotFoundException, OrderStatusUpdateException, ValidationException {
        orderService.changeOrderStatus(changeOrderStatusRequest);
        return ResponseEntity.status(204).body("");
    }

    // get order by user
    @GetMapping(path = "/get-by-user")
    public ResponseEntity<?> getOrderByUser(
            @RequestParam(defaultValue = "", name = "userId") String userId
    ) throws ValidationException {
        return ResponseEntity.status(200).body(orderService.getOrderByUser(userId));
    }
}
