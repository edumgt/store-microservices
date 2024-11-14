package com.praveenukkoji.orderservice.dto.order.response;

import com.praveenukkoji.orderservice.model.OrderItem;
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
    private String orderStatus;
    private String paymentStatus;
    private List<OrderItem> orderItemList;
    private String addressId;
    private String userId;
    private LocalDateTime createdOn;
    private UUID createdBy;
    private LocalDateTime modifiedOn;
    private UUID modifiedBy;
}
