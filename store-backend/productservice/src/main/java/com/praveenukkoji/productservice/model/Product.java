package com.praveenukkoji.productservice.model;

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
@Table(name = "product_entity")
public class Product {
    @Id
    @GeneratedValue
    private UUID productId;

    private String productName;
    private String productDescription;
    private Double productPrice;
    private Integer productQuantity;

    private LocalDateTime createdOn;
    private UUID createdBy;
    private LocalDateTime modifiedOn;
    private UUID modifiedBy;

    @ManyToMany
    @JoinTable(
            name = "category_product_relation_entity",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> category;
}
