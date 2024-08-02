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
@Table(name = "category_entity")
public class Category {
    @Id
    @GeneratedValue
    private UUID categoryId;

    private String categoryName;

    private LocalDateTime createdOn;
    private UUID createdBy;
    private LocalDateTime modifiedOn;
    private UUID modifiedBy;

    @ManyToMany(mappedBy = "category")
    private List<Product> productList;
}
