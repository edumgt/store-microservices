package com.praveenukkoji.orderservice.repository;

import com.praveenukkoji.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    // get order by user
    @Query("""
            SELECT o FROM Order o WHERE o.userId = :userId
            """)
    List<Order> findOrderByUserId(String userId);
}
