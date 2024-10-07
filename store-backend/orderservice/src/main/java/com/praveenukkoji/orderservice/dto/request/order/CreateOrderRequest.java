package com.praveenukkoji.orderservice.dto.request.order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {
    @NotNull(message = "item list is null")
    @NotEmpty(message = "item list is empty")
    private List<@Valid Item> itemList;

    @NotNull(message = "createdBy is null")
    private UUID createdBy;

    @NotNull(message = "addressId is null")
    private UUID addressId;
}
