package com.praveenukkoji.productservice.dto.response.product;

import com.praveenukkoji.productservice.dto.response.category.CategoryResponse;
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
    private UUID id;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private CategoryResponse category;
}
