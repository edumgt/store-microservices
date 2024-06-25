package com.praveenukkoji.inventoryservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateQuantityRequest {
    private UUID product_id;

    private Integer product_qty;

    private UUID modified_by;
}
