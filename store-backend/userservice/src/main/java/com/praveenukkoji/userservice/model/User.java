package com.praveenukkoji.userservice.model;

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

import static jakarta.persistence.FetchType.LAZY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "_user")
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue
    private UUID id;

    private String fullname;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    private String password;

    private Boolean isActive;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdOn;

    private UUID createdBy;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime modifiedOn;

    private UUID modifiedBy;

    @ManyToOne(fetch = LAZY)
    private Role role;

    @OneToMany(cascade = CascadeType.ALL, fetch = LAZY, mappedBy = "user", orphanRemoval = true)
    private List<Address> addressList;
}
