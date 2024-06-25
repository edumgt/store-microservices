package com.praveenukkoji.inventoryservice.model;

import jakarta.persistence.Entity;
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
@Table(name = "inventory_entity")
public class Inventory {
    @Id
    private UUID product_id;

    private Integer product_qty;

    private LocalDate created_on;

    private UUID modified_by;
}
