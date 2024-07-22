package com.praveenukkoji.productservice.dto.request.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateProductRequest {
    private String productName;
    private String productDescription;
    private Double productPrice;
    private Integer productQuantity;
    private UUID createdBy;
}
