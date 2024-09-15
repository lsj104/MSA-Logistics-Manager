package com.team12.order_delivery.delivery.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team12.common.customPage.CustomPageResponse;
import com.team12.common.dto.slack.SlackRequestDto;
import com.team12.common.dto.slack.SlackTemplate;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
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
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Transactional
    public DeliveryResDto createDelivery(DeliveryReqDto deliveryReqDto) {
        try {
            Delivery delivery = Delivery.builder()
                    .orderId(deliveryReqDto.getOrderId())
                    .departmentId(deliveryReqDto.getDepartmentId())
                    .arrivalHubId(deliveryReqDto.getArrivalHubId())
                    .address(deliveryReqDto.getAddress())
                    .receiver(deliveryReqDto.getReceiver())
                    .receiverEmail(deliveryReqDto.getReceiverEmail())
                    .deliveryStatus(Delivery.DeliveryStatus.PREPARING)
                    .build();
            delivery.setCreatedBy(0L);
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
            Delivery delivery = deliveryRespository.findById(UUID.fromString(deliveryId)).orElseThrow(() -> new BusinessLogicException(ExceptionCode.DELIVERY_NOT_FOUND));
            return new DeliveryResDto(delivery);
        } catch (BusinessLogicException e) {
            throw e;
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new BusinessLogicException(ExceptionCode.INVALID_PARAMETER);
        }
    }


    public CustomPageResponse<DeliveryResDto> getAllDelivery(Pageable pageable) {
        try {
            return new CustomPageResponse<>(deliveryRespository.findAll(pageable).map(DeliveryResDto::new));
        } catch (Exception e) {
            throw new BusinessLogicException(ExceptionCode.INVALID_PARAMETER);
        }
    }

    public DeliveryResDto updateDelivery(String deliveryId, DeliveryReqDto deliveryReqDto) {
        try {
            Delivery delivery = deliveryRespository.findById(UUID.fromString(deliveryId)).orElseThrow(() -> new BusinessLogicException(ExceptionCode.DELIVERY_NOT_FOUND));
            delivery.update(deliveryReqDto);
            deliveryRespository.save(delivery);
            return new DeliveryResDto(delivery);
        } catch (BusinessLogicException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessLogicException(ExceptionCode.INVALID_PARAMETER);
        }
    }

    public void deleteDelivery(String deliveryId) {
        try {
            Delivery delivery = deliveryRespository.findById(UUID.fromString(deliveryId)).orElseThrow(() -> new BusinessLogicException(ExceptionCode.DELIVERY_NOT_FOUND));
            deliveryRespository.delete(delivery);
        } catch (BusinessLogicException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessLogicException(ExceptionCode.INVALID_PARAMETER);
        }
    }

    @Transactional
    public DeliveryResDto updateDeliveryStatus(String deliveryId, Delivery.DeliveryStatus deliveryStatus) {
        try {
            Delivery delivery = deliveryRespository.findById(UUID.fromString(deliveryId))
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.DELIVERY_NOT_FOUND));
            delivery.setDeliveryStatus(deliveryStatus);
            deliveryRespository.save(delivery);
            return new DeliveryResDto(delivery);
        } catch (BusinessLogicException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessLogicException(ExceptionCode.INVALID_PARAMETER);
        }

    }

    @Transactional
    public RouteResDto updateDeliveryRouteStatus(String deliveryRouteId, String deliveryRouteStatus) {
        try {
            DeliveryRoute deliveryRoute = deliveryRouteRepository.findById(UUID.fromString(deliveryRouteId))
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.DELIVERY_NOT_FOUND));
            DeliveryRoute.RouteStatus newStatus = DeliveryRoute.RouteStatus.valueOf(deliveryRouteStatus);
            Delivery delivery = deliveryRespository.findById(deliveryRoute.getDeliveryId())
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.DELIVERY_NOT_FOUND));
            String content;
            if (deliveryRoute.getSequence() == 1 && newStatus == DeliveryRoute.RouteStatus.DELIVERING) {
                updateDeliveryStatus(String.valueOf(deliveryRoute.getDeliveryId()), Delivery.DeliveryStatus.DELIVERING);
                content = SlackTemplate.startDelivery(deliveryRoute.getDeliveryId().toString(), newStatus.toString());

            } else if (isLastRoute(deliveryRoute) && newStatus == DeliveryRoute.RouteStatus.ARRIVED) {
                updateDeliveryStatus(String.valueOf(deliveryRoute.getDeliveryId()), Delivery.DeliveryStatus.DELIVERED);
                content = SlackTemplate.endDelivery(deliveryRoute.getDeliveryId().toString(), newStatus.toString());
            } else {
                content = SlackTemplate.updateDeliveryStatus(deliveryRoute.getDeliveryId().toString(), newStatus.toString());
            }

            SlackRequestDto slackRequestDto = SlackRequestDto.builder()
                    .email(delivery.getReceiverEmail())
                    .content(content)
                    .build();
            ObjectMapper objectMapper = new ObjectMapper();
            try{
                objectMapper.writeValueAsString(slackRequestDto);
                kafkaTemplate.send("delivery-status-update", objectMapper.writeValueAsString(slackRequestDto));
            } catch (JsonProcessingException e) {
                log.error("Failed to serialize object: {}", slackRequestDto, e);
                throw new RuntimeException("Serialization error", e);
            }

            deliveryRoute.setStatus(newStatus);
            deliveryRouteRepository.save(deliveryRoute);
            return new RouteResDto(deliveryRoute);
        } catch (BusinessLogicException e) {
            throw e;
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new BusinessLogicException(ExceptionCode.INVALID_PARAMETER);
        }
    }

    private boolean isLastRoute(DeliveryRoute route) {
        int totalRoutes = deliveryRouteRepository.countByDeliveryId(route.getDeliveryId());
        return totalRoutes == route.getSequence();
    }

}
