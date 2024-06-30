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
    private UUID productId;

    private String productName;

    private String productDesc;

    private Double productPrice;

    private Integer productQty;

    private LocalDate createdOn;

    private UUID createdBy;

    private UUID modifiedBy;
}
