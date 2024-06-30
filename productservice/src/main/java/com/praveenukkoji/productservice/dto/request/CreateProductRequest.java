package com.praveenukkoji.productservice.dto.request;

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
    private String productDesc;
    private Double productPrice;
    private Integer productQty;
    private UUID createdBy;
}
