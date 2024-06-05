package com.praveenukkoji.productservice.dto;

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
public class ProductDetailResponse {

    private UUID product_id;

    private String product_name;

    private String product_desc;

    private Double product_price;

    private Integer product_qty;

    private LocalDate created_on;

    private UUID created_by;

    private UUID modified_by;
}