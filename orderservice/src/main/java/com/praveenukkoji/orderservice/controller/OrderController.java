package com.praveenukkoji.orderservice.controller;

import com.praveenukkoji.orderservice.dto.request.CreateOrderRequest;
import com.praveenukkoji.orderservice.dto.response.GetOrderResponse;
import com.praveenukkoji.orderservice.exception.CreateOrderException;
import com.praveenukkoji.orderservice.exception.OrderNotFoundException;
import com.praveenukkoji.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/orders/")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping(path = "/create")
    public ResponseEntity<UUID> createOrder(@RequestBody CreateOrderRequest createOrderRequest)
            throws CreateOrderException {
        return ResponseEntity.status(201).body(orderService.createOrder(createOrderRequest));
    }

    @GetMapping(path = "/get/{orderId}")
    public ResponseEntity<GetOrderResponse> getOrder(@PathVariable UUID orderId)
            throws OrderNotFoundException {
        return ResponseEntity.status(200).body(orderService.getOrder(orderId));
    }
}
