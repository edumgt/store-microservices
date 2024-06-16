package com.praveenukkoji.productservice.dto.inventory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddQuantityRequest {

    private UUID product_id;

    private Integer product_qty;

    private UUID created_by;
}
