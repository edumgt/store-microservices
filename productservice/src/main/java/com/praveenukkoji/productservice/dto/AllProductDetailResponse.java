package com.praveenukkoji.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AllProductDetailResponse {
    private Integer total_products;
    private List<ProductDetailResponse> product_list;
}
