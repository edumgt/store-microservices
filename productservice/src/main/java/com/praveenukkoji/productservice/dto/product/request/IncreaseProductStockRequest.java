package com.praveenukkoji.productservice.dto.product.request;

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
public class IncreaseProductStockRequest {
    @NotNull(message = "product id is null")
    private String productId;

    @NotNull(message = "quantity is null")
    private Integer quantity;
}
