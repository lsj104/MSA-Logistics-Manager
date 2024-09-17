package com.team12.order_delivery.order.repository;

import com.team12.order_delivery.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {

}
