package com.team12.order_delivery.deliveryRoute.service;

import com.team12.common.dto.hub.HubPathDetailsResponseDto;
import com.team12.common.dto.hub.HubPathOptimalRequestDto;
import com.team12.common.exception.BusinessLogicException;
import com.team12.common.exception.ExceptionCode;
import com.team12.order_delivery.delivery.domain.Delivery;
import com.team12.order_delivery.deliveryRoute.client.HubClient;
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
    private final HubClient hubClient;

    public void createDeliveryRoutes(Delivery delivery) {

        HubPathOptimalRequestDto hubPathOptimalRequestDto = new HubPathOptimalRequestDto();
        hubPathOptimalRequestDto.setDepartureHubID(delivery.getDepartmentId());
        hubPathOptimalRequestDto.setArrivalHubID(delivery.getArrivalHubId());
        List<HubPathDetailsResponseDto> hubMovments = hubClient.findOptimalPath(hubPathOptimalRequestDto);

        try {
            int sequence = 1;
            for (HubPathDetailsResponseDto movement : hubMovments) {
                DeliveryRoute deliveryRoute = DeliveryRoute.builder()
                        .deliveryId(delivery.getId())
                        .sequence(sequence)
                        .fromHubId(movement.getFromHubId())
                        .toHubId(movement.getToHubId())
                        .status(DeliveryRoute.RouteStatus.WAITING)
                        .estimatedTime((double) movement.getDuration())
                        .estimatedDistance((double) movement.getDistance())
                        .build();
                deliveryRouteRepository.save(deliveryRoute);
                sequence++;
            }
        } catch (Exception e) {
            throw new BusinessLogicException(ExceptionCode.INVALID_PARAMETER);
        }

    }

    public List<RouteResDto> getAllDeliveryRoutes(String deliveryId) {
        try {
            List<DeliveryRoute> deliveryRoutes = deliveryRouteRepository.findByDeliveryId(UUID.fromString(deliveryId));
            return deliveryRoutes.stream()
                    .map(RouteResDto::new)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BusinessLogicException(ExceptionCode.INVALID_PARAMETER);
        }
    }

    public RouteResDto getDeliveryRoute(String deliveryRouteId) {
        try {
            DeliveryRoute deliveryRoute = findById(UUID.fromString(deliveryRouteId));

            return new RouteResDto(deliveryRoute);
        } catch (Exception e) {
            throw new BusinessLogicException(ExceptionCode.INVALID_PARAMETER);
        }
    }

    public void deleteDeliveryRoute(String deliveryRouteId) {
        try {
            DeliveryRoute deliveryRoute = findById(UUID.fromString(deliveryRouteId));
            deliveryRouteRepository.delete(deliveryRoute);
        } catch (Exception e) {
            throw new BusinessLogicException(ExceptionCode.INVALID_PARAMETER);
        }
    }

    public RouteResDto updateDeliveryRoute(String deliveryRouteId, String deliveryId, String sequence, String hubId) {
        try {
            DeliveryRoute deliveryRoute = findById(UUID.fromString(deliveryRouteId));
            deliveryRoute.setDeliveryId(UUID.fromString(deliveryId));
            deliveryRoute.setSequence(Integer.parseInt(sequence));
            deliveryRoute.setFromHubId(UUID.fromString(hubId));
            deliveryRouteRepository.save(deliveryRoute);
            return new RouteResDto(deliveryRoute);
        } catch (Exception e) {
            throw new BusinessLogicException(ExceptionCode.INVALID_PARAMETER);
        }
    }

    public DeliveryRoute findById(UUID deliveryRouteId) {
        return deliveryRouteRepository.findById(deliveryRouteId)
                .orElseThrow(() -> new EntityNotFoundException("해당 배송 경로를 찾을 수 없습니다."));
    }
}
