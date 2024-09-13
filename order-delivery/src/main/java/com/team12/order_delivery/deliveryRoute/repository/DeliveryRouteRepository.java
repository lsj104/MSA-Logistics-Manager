package com.team12.order_delivery.deliveryRoute.repository;

import com.team12.order_delivery.deliveryRoute.domain.DeliveryRoute;
import com.team12.order_delivery.deliveryRoute.dto.RouteResDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DeliveryRouteRepository extends JpaRepository<DeliveryRoute, UUID> {
    List<DeliveryRoute> findByDeliveryId(UUID deliveryId);

    int countByDeliveryId(UUID deliveryId);
}
