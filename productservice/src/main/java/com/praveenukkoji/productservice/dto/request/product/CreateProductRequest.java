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
public class CreateProductRequest {
    @NotNull(message = "product name is null")
    @NotEmpty(message = "product name is empty")
    private String name;

    @NotNull(message = "description is null")
    @NotEmpty(message = "description is empty")
    private String description;

    @NotNull(message = "price is null")
    private Double price;

    @NotNull(message = "quantity is null")
    private Integer quantity;

    @NotNull(message = "category id is null")
    @NotEmpty(message = "category id is empty")
    private String categoryId;
}
