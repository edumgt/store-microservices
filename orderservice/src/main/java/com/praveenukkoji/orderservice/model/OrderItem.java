package com.praveenukkoji.orderservice.model;

import com.praveenukkoji.orderservice.model.key.CompositeKey;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
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
@IdClass(CompositeKey.class)
public class OrderItem {
    @Id
    private UUID order_id;

    @Id
    private UUID product_id;

    private Double product_price;

    private Integer product_qty;
}
