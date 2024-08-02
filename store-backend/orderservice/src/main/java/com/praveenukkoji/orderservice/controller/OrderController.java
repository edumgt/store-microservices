package com.praveenukkoji.orderservice.controller;

import com.praveenukkoji.orderservice.dto.Response;
import com.praveenukkoji.orderservice.dto.request.CreateOrderRequest;
import com.praveenukkoji.orderservice.exception.CreateOrderException;
import com.praveenukkoji.orderservice.exception.OrderNotFoundException;
import com.praveenukkoji.orderservice.exception.ProductDoesNotExist;
import com.praveenukkoji.orderservice.exception.QuantityNotAvailable;
import com.praveenukkoji.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/orders/")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping(path = "/create")
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequest createOrderRequest)
            throws CreateOrderException, QuantityNotAvailable, ProductDoesNotExist {
        return ResponseEntity.status(201).body(orderService.createOrder(createOrderRequest));
    }

    @GetMapping(path = "/get")
    public ResponseEntity<?> getOrder(
            @RequestParam(defaultValue = "", name = "orderId") String orderId
    ) throws OrderNotFoundException {
        if (Objects.equals(orderId, "")) {
            Response response = Response.builder()
                    .message("order id is empty")
                    .build();

            return ResponseEntity.status(400).body(response);
        }

        UUID id = UUID.fromString(orderId);
        return ResponseEntity.status(200).body(orderService.getOrder(id));
    }
}
