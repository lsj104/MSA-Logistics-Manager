package com.team12.order_delivery.deliveryRoute.service;

import com.team12.order_delivery.delivery.domain.Delivery;
import com.team12.order_delivery.deliveryRoute.domain.DeliveryRoute;
import com.team12.order_delivery.deliveryRoute.dto.RouteResDto;
import com.team12.order_delivery.deliveryRoute.repository.DeliveryRouteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryRouteService {
    private final DeliveryRouteRepository deliveryRouteRepository;

    public void createDeliveryRoutes(Delivery delivery) {

        // TODO : 허브 간 이동 경로 가져옴


//        int sequence = 1; // 경로 순번
//        for (HubMovement movement : hubMovements) {
//            DeliveryRoute deliveryRoute = new DeliveryRoute();
//            deliveryRoute.setDeliveryId(delivery.getId());
//            deliveryRoute.setSequence((long) sequence++);
//            deliveryRoute.setSourceHubId(movement.getStartHubId());
//            deliveryRoute.setDestinationHubId(movement.getEndHubId());
//            deliveryRoute.setStatus(RouteStatus.WAITING_FOR_TRANSIT);
//        }

        try{
            DeliveryRoute deliveryRoute = DeliveryRoute.builder()
                    .deliveryId(delivery.getId())
                    .sequence(1)
                    .fromHubId(UUID.randomUUID())
                    .toHubId(UUID.randomUUID())
                    .status(DeliveryRoute.RouteStatus.WAITING)
                    .estimatedTime(null)
                    .estimatedDistance(null)
                    .build();

            deliveryRouteRepository.save(deliveryRoute);
            deliveryRoute = DeliveryRoute.builder()
                    .deliveryId(delivery.getId())
                    .sequence(2)
                    .fromHubId(UUID.randomUUID())
                    .toHubId(UUID.randomUUID())
                    .status(DeliveryRoute.RouteStatus.WAITING)
                    .estimatedTime(null)
                    .estimatedDistance(null)
                    .build();
            deliveryRouteRepository.save(deliveryRoute);
            new RouteResDto(deliveryRoute);
        } catch (Exception e) {
            log.info("createDeliveryRoutes error");
            log.info(e.getMessage());
        }

    }

    public List<RouteResDto> getAllDeliveryRoutes(String deliveryId) {
        List<DeliveryRoute> deliveryRoutes = deliveryRouteRepository.findByDeliveryId(UUID.fromString(deliveryId));
        return deliveryRoutes.stream()
                .map(RouteResDto::new)
                .collect(Collectors.toList());
    }

    public RouteResDto getDeliveryRoute(String deliveryRouteId) {
        return new RouteResDto(deliveryRouteRepository.findById(UUID.fromString(deliveryRouteId)).orElseThrow());
    }

    public void deleteDeliveryRoute(String deliveryRouteId) {
        deliveryRouteRepository.deleteById(UUID.fromString(deliveryRouteId));
    }

    public RouteResDto updateDeliveryRoute(String deliveryRouteId, String deliveryId, String sequence, String hubId) {
        DeliveryRoute deliveryRoute = deliveryRouteRepository.findById(UUID.fromString(deliveryRouteId))
                .orElseThrow(() -> new EntityNotFoundException("DeliveryRoute not found with id: " + deliveryRouteId));

        deliveryRoute.setDeliveryId(UUID.fromString(deliveryId));
        deliveryRoute.setSequence(Integer.parseInt(sequence));
        deliveryRoute.setFromHubId(UUID.fromString(hubId));
        deliveryRouteRepository.save(deliveryRoute);
        return new RouteResDto(deliveryRoute);
    }
}
