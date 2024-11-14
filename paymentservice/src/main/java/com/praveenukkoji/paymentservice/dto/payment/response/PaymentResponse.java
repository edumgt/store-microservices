package com.praveenukkoji.paymentservice.dto.payment.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {
    private UUID id;
    private Double amount;
    private String paymentStatus;
    private String orderId;
    private LocalDateTime createdOn;
    private UUID createdBy;
    private LocalDateTime modifiedOn;
    private UUID modifiedBy;
}
