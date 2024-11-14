package com.praveenukkoji.orderservice.model;

import lombok.Getter;

@Getter
public enum OrderStatus {
    CREATED("created"),
    CANCELED("canceled"),
    PLACED("placed"),
    OUT_FOR_DELIVERY("out-for-delivery"),
    DELIVERED("delivered");

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }
}
