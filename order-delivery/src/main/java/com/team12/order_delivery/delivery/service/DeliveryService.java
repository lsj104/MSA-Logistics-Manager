package com.team12.order_delivery.delivery.service;

import com.team12.common.customPage.CustomPageResponse;
import com.team12.common.exception.BusinessLogicException;
import com.team12.common.exception.ExceptionCode;
import com.team12.order_delivery.delivery.domain.Delivery;
import com.team12.order_delivery.delivery.dto.DeliveryReqDto;
import com.team12.order_delivery.delivery.dto.DeliveryResDto;
import com.team12.order_delivery.delivery.repository.DeliveryRespository;
import com.team12.order_delivery.deliveryRoute.domain.DeliveryRoute;
import com.team12.order_delivery.deliveryRoute.dto.RouteResDto;
import com.team12.order_delivery.deliveryRoute.repository.DeliveryRouteRepository;
import com.team12.order_delivery.deliveryRoute.service.DeliveryRouteService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryRespository deliveryRespository;
    private final DeliveryRouteRepository deliveryRouteRepository;
    private final DeliveryRouteService deliveryRouteService;

    @Transactional
    public DeliveryResDto createDelivery(DeliveryReqDto deliveryReqDto) {
        try {
            Delivery delivery = Delivery.builder()
                    .orderId(deliveryReqDto.getOrderId())
                    .fromHubId(deliveryReqDto.getFromHubId())
                    .toHubId(deliveryReqDto.getToHubId())
                    .address(deliveryReqDto.getAddress())
                    .receiver(deliveryReqDto.getReceiver())
                    .receiverEmail(deliveryReqDto.getReceiverEmail())
                    .deliveryStatus(Delivery.DeliveryStatus.PREPARING)
                    .build();

            deliveryRespository.save(delivery);
            deliveryRouteService.createDeliveryRoutes(delivery);
            return new DeliveryResDto(delivery);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new BusinessLogicException(ExceptionCode.INVALID_PARAMETER);
        }

    }



    public DeliveryResDto getDelivery(String deliveryId) {
        try {
            return new DeliveryResDto(deliveryRespository.findById(UUID.fromString(deliveryId)).orElseThrow(() ->
                    new BusinessLogicException(ExceptionCode.INVALID_PARAMETER)));
        } catch (Exception e) {
            throw new BusinessLogicException(ExceptionCode.INVALID_PARAMETER);
        }
    }


    public CustomPageResponse<DeliveryResDto> getAllDelivery(Pageable pageable) {
        try {
            return new CustomPageResponse<>(deliveryRespository.findAll(pageable).map(DeliveryResDto::new));
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new BusinessLogicException(ExceptionCode.INVALID_PARAMETER);
        }
    }

    public DeliveryResDto updateDelivery(String deliveryId, DeliveryReqDto deliveryReqDto) {
        try {
            Delivery delivery = deliveryRespository.findById(UUID.fromString(deliveryId)).orElseThrow(() -> new IllegalArgumentException("배송 정보가 없습니다."));
            delivery.update(deliveryReqDto);
            deliveryRespository.save(delivery);
            return new DeliveryResDto(delivery);
        } catch (Exception e) {
            throw new BusinessLogicException(ExceptionCode.INVALID_PARAMETER);
        }
    }

    public void deleteDelivery(String deliveryId) {
        try {
            Delivery delivery = deliveryRespository.findById(UUID.fromString(deliveryId)).orElseThrow(() -> new IllegalArgumentException("배송 정보가 없습니다."));
            deliveryRespository.delete(delivery);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new BusinessLogicException(ExceptionCode.INVALID_PARAMETER);
        }
    }

    @Transactional
    public DeliveryResDto updateDeliveryStatus(String deliveryId, String deliveryStatus) {
        try {
            Delivery delivery = deliveryRespository.findById(UUID.fromString(deliveryId))
                    .orElseThrow(() -> new EntityNotFoundException("배송 정보가 없습니다."));
            delivery.setDeliveryStatus(Delivery.DeliveryStatus.valueOf(deliveryStatus));
            deliveryRespository.save(delivery);
            return new DeliveryResDto(delivery);
        } catch (IllegalArgumentException e) {
            log.error("Invalid status or UUID format: ", e);
            throw new BusinessLogicException(ExceptionCode.INVALID_PARAMETER);
        } catch (EntityNotFoundException e) {
            log.error("Delivery not found: ", e);
            throw new BusinessLogicException(ExceptionCode.ENTITY_NOT_FOUND);
        } catch (Exception e) {
            log.error("Unexpected error in updateDeliveryStatus: ", e);
            return null;
        }
    }

    @Transactional
    public RouteResDto updateDeliveryRouteStatus(String deliveryRouteId, String deliveryRouteStatus) {
        try {
            DeliveryRoute deliveryRoute = deliveryRouteRepository.findById(UUID.fromString(deliveryRouteId))
                    .orElseThrow(() -> new EntityNotFoundException("DeliveryRoute not found with id: " + deliveryRouteId));

            DeliveryRoute.RouteStatus newStatus = DeliveryRoute.RouteStatus.valueOf(deliveryRouteStatus);

            if(deliveryRoute.getSequence() == 1 && newStatus == DeliveryRoute.RouteStatus.DELIVERING) {
                updateDeliveryStatus(String.valueOf(deliveryRoute.getDeliveryId()), "DELIVERING");
            } else if (isLastRoute(deliveryRoute) && newStatus == DeliveryRoute.RouteStatus.ARRIVED) {
                updateDeliveryStatus(String.valueOf(deliveryRoute.getDeliveryId()), "DELIVERED");
            }

            deliveryRoute.setStatus(newStatus);
            deliveryRouteRepository.save(deliveryRoute);
            return new RouteResDto(deliveryRoute);
        } catch (Exception e) {
            throw new BusinessLogicException(ExceptionCode.INVALID_PARAMETER);
        }
    }

    private boolean isLastRoute(DeliveryRoute route) {
        int totalRoutes = deliveryRouteRepository.countByDeliveryId(route.getDeliveryId());
        return totalRoutes == route.getSequence();
    }

}
