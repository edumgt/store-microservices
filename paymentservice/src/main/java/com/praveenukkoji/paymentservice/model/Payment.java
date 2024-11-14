package com.praveenukkoji.paymentservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "_payment")
@EntityListeners(AuditingEntityListener.class)
public class Payment {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String paymentStatus;

    @Column(nullable = false)
    private String orderId;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdOn;

    private UUID createdBy;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime modifiedOn;

    private UUID modifiedBy;
}
