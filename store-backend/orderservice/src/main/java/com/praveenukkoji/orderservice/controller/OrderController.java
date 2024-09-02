package com.praveenukkoji.orderservice.controller;

import com.praveenukkoji.orderservice.dto.error.ValidationResponse;
import com.praveenukkoji.orderservice.dto.request.order.CreateOrderRequest;
import com.praveenukkoji.orderservice.exception.order.CreateOrderException;
import com.praveenukkoji.orderservice.exception.order.OrderNotFoundException;
import com.praveenukkoji.orderservice.exception.product.ProductNotFoundException;
import com.praveenukkoji.orderservice.exception.product.ProductNotInStock;
import com.praveenukkoji.orderservice.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping(path = "")
    public ResponseEntity<?> createOrder(
            @RequestBody @Valid CreateOrderRequest createOrderRequest
    ) throws CreateOrderException, ProductNotInStock, ProductNotFoundException {
        return ResponseEntity.status(201).body(orderService.createOrder(createOrderRequest));
    }

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
}
