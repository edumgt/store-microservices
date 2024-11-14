package com.praveenukkoji.orderservice.dto.order.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangeOrderStatusRequest {
    @NotNull(message = "order id is null")
    private String orderId;

    @NotNull(message = "order status is null")
    private String orderStatus;
}
