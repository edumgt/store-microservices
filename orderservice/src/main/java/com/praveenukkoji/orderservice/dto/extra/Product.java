package com.praveenukkoji.orderservice.dto.extra;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private UUID product_id;

    private Double product_price;

    private Integer product_qty;
}
