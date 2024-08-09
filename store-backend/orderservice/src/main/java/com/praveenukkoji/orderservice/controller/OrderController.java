package com.praveenukkoji.orderservice.controller;

import com.praveenukkoji.orderservice.dto.error.ErrorResponse;
import com.praveenukkoji.orderservice.dto.request.order.CreateOrderRequest;
import com.praveenukkoji.orderservice.exception.order.CreateOrderException;
import com.praveenukkoji.orderservice.exception.order.OrderNotFoundException;
import com.praveenukkoji.orderservice.exception.payment.PaymentNotFoundException;
import com.praveenukkoji.orderservice.exception.product.ProductDoesNotExist;
import com.praveenukkoji.orderservice.exception.product.QuantityNotAvailable;
import com.praveenukkoji.orderservice.service.OrderService;
import jakarta.validation.Valid;
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
    public ResponseEntity<?> createOrder(
            @RequestBody @Valid CreateOrderRequest createOrderRequest
    ) throws CreateOrderException, QuantityNotAvailable, ProductDoesNotExist {

        return ResponseEntity.status(201).body(orderService.createOrder(createOrderRequest));
    }

    @GetMapping(path = "/get")
    public ResponseEntity<?> getOrder(
            @RequestParam(defaultValue = "", name = "orderId") String orderId
    ) throws OrderNotFoundException, PaymentNotFoundException {

        if (Objects.equals(orderId, "")) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .message("order id is empty")
                    .build();

            return ResponseEntity.status(400).body(errorResponse);
        }

        UUID id = UUID.fromString(orderId);
        return ResponseEntity.status(200).body(orderService.getOrder(id));
    }
}
