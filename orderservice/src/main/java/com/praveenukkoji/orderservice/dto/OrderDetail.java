package com.praveenukkoji.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetail {

    private UUID orderId;

    private List<Product> product_list;

    private Integer total_items;

    private Double total_amount;

    private LocalDate created_on;

    private UUID created_by;
}
