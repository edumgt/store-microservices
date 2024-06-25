package com.praveenukkoji.productservice.model;

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
@Table(name = "product_entity")
public class Product {
    @Id
    @GeneratedValue
    private UUID product_id;

    private String product_name;

    private String product_desc;

    private Double product_price;

    private LocalDate created_on;

    private UUID created_by;

    private UUID modified_by;
}
