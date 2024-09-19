package com.team12.order_delivery.delivery.repository;

import com.team12.order_delivery.delivery.domain.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DeliveryRespository extends JpaRepository<Delivery, UUID> {
}
