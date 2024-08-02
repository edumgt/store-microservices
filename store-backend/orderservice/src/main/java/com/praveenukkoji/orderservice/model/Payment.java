package com.praveenukkoji.orderservice.model;

import com.praveenukkoji.orderservice.model.types.PaymentStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "payment_entity")
public class Payment {
    @Id
    @GeneratedValue
    private UUID paymentId;

    private Double paymentAmount;

    private PaymentStatus paymentStatus;

    private LocalDateTime createdOn;
    private UUID createdBy;
    private LocalDateTime modifiedOn;
}
