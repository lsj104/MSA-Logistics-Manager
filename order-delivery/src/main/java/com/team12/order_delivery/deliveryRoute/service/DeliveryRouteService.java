package com.team12.order_delivery.deliveryRoute.service;

import com.team12.common.dto.hub.HubPathDetailsResponseDto;
import com.team12.common.dto.hub.HubPathOptimalRequestDto;
import com.team12.common.dto.hub.HubResponseDto;
import com.team12.common.dto.hub.ManagerResponseDto;
import com.team12.common.exception.BusinessLogicException;
import com.team12.common.exception.ExceptionCode;
import com.team12.order_delivery.delivery.domain.Delivery;
import com.team12.order_delivery.deliveryRoute.client.HubClient;
import com.team12.order_delivery.deliveryRoute.domain.DeliveryRoute;
import com.team12.order_delivery.deliveryRoute.dto.RouteResDto;
import com.team12.order_delivery.deliveryRoute.repository.DeliveryRouteRepository;
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
        List<ManagerResponseDto> managers = hubClient.getHubToHubManagers();

        try {
            int sequence = 1;
            for (HubPathDetailsResponseDto movement : hubMovments) {
                String fromHubName = findHubNameById(movement.getFromHubId());
                String toHubName = findHubNameById(movement.getToHubId());
                DeliveryRoute deliveryRoute = DeliveryRoute.builder()
                        .delivery(delivery)
                        .sequence(sequence)
                        .startPoint(fromHubName)
                        .endPoint(toHubName)
                        .status(DeliveryRoute.RouteStatus.WAITING)
                        .estimatedTime((double) movement.getDuration())
                        .estimatedDistance((double) movement.getDistance())
                        .deliveryPersonId(managers.get(0).getId())
                        .build();
                deliveryRouteRepository.save(deliveryRoute);
                sequence++;
            }

            DeliveryRoute lastRoute = DeliveryRoute.builder()
                    .delivery(delivery)
                    .sequence(sequence)
                    .startPoint(findHubNameById(hubMovments.get(hubMovments.size() - 1).getToHubId()))
                    .endPoint(delivery.getAddress())
                    .status(DeliveryRoute.RouteStatus.WAITING)
                    .estimatedTime(0.0)
                    .estimatedDistance(0.0)
                    .deliveryPersonId(hubClient.getHubToCompanyManagers(delivery.getArrivalHubId()).get(0).getId())
                    .build();
            deliveryRouteRepository.save(lastRoute);

        } catch (Exception e) {
            log.error(e.getMessage());
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

    public RouteResDto updateDeliveryRoute(String deliveryRouteId, String sequence, String hubId) {
        try {
            DeliveryRoute deliveryRoute = findById(UUID.fromString(deliveryRouteId));
            deliveryRoute.setSequence(Integer.parseInt(sequence));
            deliveryRoute.setStartPoint(findHubNameById(UUID.fromString(hubId)));
            deliveryRouteRepository.save(deliveryRoute);
            return new RouteResDto(deliveryRoute);
        } catch (Exception e) {
            throw new BusinessLogicException(ExceptionCode.INVALID_PARAMETER);
        }
    }

    public DeliveryRoute findById(UUID deliveryRouteId) {
        return deliveryRouteRepository.findById(deliveryRouteId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.INVALID_PARAMETER));
    }

    public String findHubNameById(UUID hubId) {
        HubResponseDto hub = hubClient.getHub(hubId).data();
        return hub.getName();
    }
}
