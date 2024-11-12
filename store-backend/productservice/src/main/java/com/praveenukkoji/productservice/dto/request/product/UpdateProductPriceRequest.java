package com.praveenukkoji.productservice.dto.request.product;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateProductPriceRequest {
    @NotNull(message = "product id is null")
    @NotEmpty(message = "product id is empty")
    private String productId;

    @NotNull(message = "product price is null")
    private Double productPrice;
}
