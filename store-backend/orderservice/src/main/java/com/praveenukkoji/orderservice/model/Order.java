package com.praveenukkoji.orderservice.model;

import com.praveenukkoji.orderservice.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static jakarta.persistence.FetchType.EAGER;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "_order")
@EntityListeners(AuditingEntityListener.class)
public class Order {
    @Id
    @GeneratedValue
    private UUID id;

    private Integer totalItems;
    private Double amount;

    private OrderStatus status;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdOn;

    private UUID createdBy;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime modifiedOn;

    private UUID modifiedBy;

    @OneToMany(cascade = CascadeType.ALL, fetch = EAGER, mappedBy = "order")
    private List<OrderItem> orderItemList;

    @OneToOne(cascade = CascadeType.ALL, fetch = EAGER)
    private Payment payment;
}
