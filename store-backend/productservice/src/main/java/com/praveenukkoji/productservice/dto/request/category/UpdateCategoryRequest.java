package com.praveenukkoji.productservice.dto.request.category;

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
public class UpdateCategoryRequest {
    @NotNull(message = "category id is null")
    @NotEmpty(message = "category id is empty")
    private String categoryId;

    @NotNull(message = "category name is null")
    @NotEmpty(message = "category name is empty")
    private String name;
}
