package com.team12.order_delivery.order.repository;

import com.team12.order_delivery.order.domain.Order;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    Page<Order> findByIsDeletedFalse(Pageable pageable);
}
