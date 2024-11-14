package com.praveenukkoji.orderservice.model;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    UN_PAID("un-paid"),
    PAID("paid"),
    FAILED("failed"),
    PROCESSING("processing");

    private final String value;

    PaymentStatus (String value) {
        this.value = value;
    }
}
