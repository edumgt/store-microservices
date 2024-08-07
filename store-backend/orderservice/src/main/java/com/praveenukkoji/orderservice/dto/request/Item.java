package com.praveenukkoji.orderservice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    @NotNull(message = "product id is null")
    private UUID id;

    @NotNull(message = "quantity is null")
    private Integer quantity;
}
