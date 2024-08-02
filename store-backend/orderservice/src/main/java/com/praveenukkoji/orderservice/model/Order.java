package com.praveenukkoji.orderservice.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.praveenukkoji.orderservice.model.types.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "order_entity")
public class Order {
    @Id
    @GeneratedValue
    private UUID orderId;

    private Integer orderItemCount;
    private Double orderAmount;

    private OrderStatus orderStatus;

    private LocalDateTime createdOn;
    private UUID createdBy;
    private LocalDateTime modifiedOn;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderItem> orderItemList;
}
