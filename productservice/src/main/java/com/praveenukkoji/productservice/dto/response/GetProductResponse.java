package com.praveenukkoji.productservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetProductResponse {
    private UUID productId;
    private String productName;
    private String productDesc;
    private Double productPrice;
    private Integer productQty;
    private LocalDate createdOn;
    private UUID createdBy;
    private UUID modifiedBy;
}