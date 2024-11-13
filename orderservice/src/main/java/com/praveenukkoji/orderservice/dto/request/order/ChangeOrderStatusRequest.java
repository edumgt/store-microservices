package com.praveenukkoji.orderservice.dto.request.order;

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
    @NotEmpty(message = "order id is empty")
    private String orderId;

    @NotNull(message = "order status is null")
    @NotEmpty(message = "order status is empty")
    private String orderStatus;
}
