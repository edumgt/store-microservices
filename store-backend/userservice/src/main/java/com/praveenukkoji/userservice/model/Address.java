package com.praveenukkoji.userservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "_address")
@EntityListeners(AuditingEntityListener.class)
public class Address {
    @Id
    @GeneratedValue
    private UUID id;

    private String line;
    private String country;
    private String state;
    private String city;
    private String pincode;

    private Boolean isDefault;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdOn;

    private UUID createdBy;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime modifiedOn;

    private UUID modifiedBy;

    @ManyToOne
    @JsonIgnore
    private User user;
}
