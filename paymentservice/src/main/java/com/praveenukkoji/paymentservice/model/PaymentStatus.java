package com.praveenukkoji.paymentservice.model;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    SUCCESS("success"),
    FAILED("failed"),
    PROCESSING("processing");

    private final String value;

    PaymentStatus(String value) {
        this.value = value;
    }
}
