package com.praveenukkoji.userservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "address_entity")
public class Address {
    @Id
    @GeneratedValue
    private UUID addressId;

    private String addressLine;
    private String addressCountry;
    private String addressState;
    private String addressCity;
    private String addressPincode;

    private Boolean isDefault;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
