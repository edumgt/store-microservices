package com.praveenukkoji.orderservice.dto.response;

import com.praveenukkoji.orderservice.dto.extra.Product;
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
public class GetOrderResponse {
    private UUID orderId;

    private List<Product> product_list;

    private Integer total_items;

    private Double total_amount;

    private String order_status;

    private LocalDate created_on;

    private UUID created_by;
}
