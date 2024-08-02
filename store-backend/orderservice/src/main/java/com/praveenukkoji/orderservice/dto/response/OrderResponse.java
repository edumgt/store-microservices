package com.praveenukkoji.orderservice.dto.response;

import com.praveenukkoji.orderservice.model.OrderItem;
import com.praveenukkoji.orderservice.model.Payment;
import com.praveenukkoji.orderservice.model.types.OrderStatus;
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
    private UUID orderId;
    private List<OrderItem> orderItemList;
    private Payment payment;
    private Integer orderItemCount;
    private Double orderAmount;
    private OrderStatus orderStatus;
    private LocalDateTime createdOn;
    private UUID createdBy;
    private LocalDateTime modifiedOn;

}
