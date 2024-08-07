package com.praveenukkoji.orderservice.repository;

import com.praveenukkoji.orderservice.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    @Query("""
            SELECT p FROM Payment p
            WHERE p.order = :orderId
            """)
    Optional<Payment> findByOrderId(UUID orderId);
}
