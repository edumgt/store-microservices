package com.praveenukkoji.userservice.model;

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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_entity")
public class User {
    @Id
    @GeneratedValue
    private UUID userId;

    private String username;

    private String email;

    private String password;

    private LocalDate createdOn;

    private UUID createdBy;

    private UUID modifiedBy;
}
