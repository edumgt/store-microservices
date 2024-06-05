package com.praveenukkoji.orderservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
    private UUID order_id;

    private Integer total_items;

    private Double total_amount;

    private LocalDate created_on;

    private UUID created_by;
}
