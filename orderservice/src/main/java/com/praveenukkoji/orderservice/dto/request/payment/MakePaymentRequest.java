package com.praveenukkoji.orderservice.dto.request.payment;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MakePaymentRequest {
    @NotNull(message = "amount is null")
    private Double amount;

    @NotNull(message = "status is null")
    @NotEmpty(message = "status is empty")
    private String status;

    @NotNull(message = "order id is null")
    private UUID orderId;
}
