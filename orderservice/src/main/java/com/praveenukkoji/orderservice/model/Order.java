package com.praveenukkoji.orderservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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

    private Integer totalItems;

    private Double totalAmount;

    private String orderStatus;

    private LocalDate createdOn;

    private UUID createdBy;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderItem> orderItems;
}
