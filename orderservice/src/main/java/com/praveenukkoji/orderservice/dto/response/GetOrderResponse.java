package com.praveenukkoji.orderservice.dto.response;

import com.praveenukkoji.orderservice.dto.Product;
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
    private List<Product> products;
    private Integer totalItems;
    private Double totalAmount;
    private String orderStatus;
    private LocalDate createdOn;
    private UUID createdBy;
}
