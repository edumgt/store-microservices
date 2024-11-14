package com.praveenukkoji.orderservice.dto.order.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {
    @NotNull(message = "item list is null")
    private List<@Valid Item> itemList;

    @NotNull(message = "user id is null")
    private String userId;

    @NotNull(message = "address id is null")
    private String addressId;
}
