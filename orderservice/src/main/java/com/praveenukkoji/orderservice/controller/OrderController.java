package com.praveenukkoji.orderservice.controller;

import com.praveenukkoji.orderservice.dto.request.CreateOrderRequest;
import com.praveenukkoji.orderservice.dto.response.GetOrderResponse;
import com.praveenukkoji.orderservice.dto.response.OrderResponse;
import com.praveenukkoji.orderservice.exception.CreateOrderException;
import com.praveenukkoji.orderservice.exception.OrderNotFoundException;
import com.praveenukkoji.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

// @RefreshScope only changes property values which are used in service etc. using @Value

@RestController
@RequestMapping("api/order/")
@RefreshScope
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping(path = "/create")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody CreateOrderRequest createOrderRequest)
            throws CreateOrderException {
        return ResponseEntity.status(201).body(orderService.createOrder(createOrderRequest));
    }

    @GetMapping(path = "/get/{order_id}")
    public ResponseEntity<GetOrderResponse> getOrder(@PathVariable UUID order_id)
            throws OrderNotFoundException {
        return ResponseEntity.status(200).body(orderService.getOrder(order_id));
    }
}
