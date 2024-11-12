package com.praveenukkoji.orderservice.dto.response.order;

import com.praveenukkoji.orderservice.model.OrderItem;
import com.praveenukkoji.orderservice.model.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private UUID id;
    private Integer totalItems;
    private Double amount;
    private String status;
    private UUID addressId;
    private LocalDateTime createdOn;
    private UUID createdBy;
    private LocalDateTime modifiedOn;
    private UUID modifiedBy;
    private Payment payment;
    private List<OrderItem> orderItemList;
}
