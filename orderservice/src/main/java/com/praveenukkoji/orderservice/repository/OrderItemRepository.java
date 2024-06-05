package com.praveenukkoji.orderservice.repository;

import com.praveenukkoji.orderservice.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {

    @Query("select oi from OrderItem oi where order_id=?1")
    public List<OrderItem> findAllById(UUID orderId);
}
