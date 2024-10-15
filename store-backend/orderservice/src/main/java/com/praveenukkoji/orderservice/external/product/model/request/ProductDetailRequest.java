package com.praveenukkoji.orderservice.external.product.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailRequest {
    private UUID productId;
    private Integer quantity;
}
