package com.praveenukkoji.paymentservice.dto.payment.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MakePaymentRequest {
    @NotNull(message = "amount is null")
    private Double amount;

    @NotNull(message = "payment status is null")
    private String paymentStatus;

    @NotNull(message = "order id is null")
    private String orderId;
}
