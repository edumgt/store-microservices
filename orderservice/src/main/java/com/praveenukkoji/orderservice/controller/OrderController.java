package com.praveenukkoji.orderservice.controller;

import com.praveenukkoji.orderservice.dto.OrderCreateRequest;
import com.praveenukkoji.orderservice.dto.OrderDetail;
import com.praveenukkoji.orderservice.exception.OrderDoesNotExistException;
import com.praveenukkoji.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/order/")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping(path = "/create")
    public ResponseEntity<UUID> createOrder(@RequestBody OrderCreateRequest orderCreateRequest) {
        return ResponseEntity.status(201).body(orderService.createOrder(orderCreateRequest));
    }

    @GetMapping(path = "/get/{order_id}")
    public ResponseEntity<OrderDetail> getOrder(@PathVariable UUID order_id)
            throws OrderDoesNotExistException {
        return ResponseEntity.status(200).body(orderService.getOrder(order_id));
    }
}
