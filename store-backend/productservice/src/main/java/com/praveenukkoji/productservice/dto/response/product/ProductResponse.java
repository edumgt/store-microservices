package com.praveenukkoji.productservice.dto.response.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private UUID productId;
    private String productName;
    private String productDescription;
    private Double productPrice;
    private Integer productQuantity;
}
