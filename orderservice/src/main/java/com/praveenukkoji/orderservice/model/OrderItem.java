package com.praveenukkoji.orderservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "order_item_entity")
public class OrderItem {
    @Id
    @GeneratedValue
    private UUID orderItemId;

    private UUID productId;
    private Integer productQuantity;
    private Double productPrice;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
