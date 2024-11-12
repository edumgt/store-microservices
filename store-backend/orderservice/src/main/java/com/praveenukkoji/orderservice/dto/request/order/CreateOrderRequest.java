package com.praveenukkoji.orderservice.dto.request.order;

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
    @NotEmpty(message = "item list is empty")
    private List<@Valid Item> itemList;

    @NotNull(message = "created by is null")
    @NotEmpty(message = "created by is empty")
    private String createdBy;

    @NotNull(message = "address id is null")
    @NotEmpty(message = "address id is empty")
    private String addressId;
}
