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
import com.team12.order_delivery.deliveryRoute.client.HubClient;
import com.team12.order_delivery.deliveryRoute.domain.DeliveryRoute;
import com.team12.order_delivery.deliveryRoute.dto.RouteResDto;
import com.team12.order_delivery.deliveryRoute.repository.DeliveryRouteRepository;
import com.team12.order_delivery.deliveryRoute.service.DeliveryRouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@EnableAsync
public class DeliveryService {
    private final DeliveryRespository deliveryRepository;
    private final DeliveryRouteRepository deliveryRouteRepository;
    private final DeliveryRouteService deliveryRouteService;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final HubClient hubClient;

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
            deliveryRepository.save(delivery);
            deliveryRouteService.createDeliveryRoutes(delivery);
            return new DeliveryResDto(delivery);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new BusinessLogicException(ExceptionCode.INVALID_PARAMETER);
        }

    }


    public DeliveryResDto getDelivery(String deliveryId) {
        try {
            Delivery delivery = deliveryRepository.findById(UUID.fromString(deliveryId)).orElseThrow(() -> new BusinessLogicException(ExceptionCode.DELIVERY_NOT_FOUND));
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
            return new CustomPageResponse<>(deliveryRepository.findAll(pageable).map(DeliveryResDto::new));
        } catch (Exception e) {
            throw new BusinessLogicException(ExceptionCode.INVALID_PARAMETER);
        }
    }

    public DeliveryResDto updateDelivery(String deliveryId, DeliveryReqDto deliveryReqDto) {
        try {
            Delivery delivery = deliveryRepository.findById(UUID.fromString(deliveryId)).orElseThrow(() -> new BusinessLogicException(ExceptionCode.DELIVERY_NOT_FOUND));
            delivery.update(deliveryReqDto);
            deliveryRepository.save(delivery);
            return new DeliveryResDto(delivery);
        } catch (BusinessLogicException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessLogicException(ExceptionCode.INVALID_PARAMETER);
        }
    }

    public void deleteDelivery(String deliveryId) {
        try {
            Delivery delivery = deliveryRepository.findById(UUID.fromString(deliveryId)).orElseThrow(() -> new BusinessLogicException(ExceptionCode.DELIVERY_NOT_FOUND));
            deliveryRepository.delete(delivery);
        } catch (BusinessLogicException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessLogicException(ExceptionCode.INVALID_PARAMETER);
        }
    }

    @Transactional
    public DeliveryResDto updateDeliveryStatus(String deliveryId, Delivery.DeliveryStatus deliveryStatus) {
        try {
            Delivery delivery = deliveryRepository.findById(UUID.fromString(deliveryId))
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.DELIVERY_NOT_FOUND));
            delivery.setDeliveryStatus(deliveryStatus);
            deliveryRepository.save(delivery);
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
            DeliveryRoute deliveryRoute = findDeliveryRouteById(deliveryRouteId);
            DeliveryRoute.RouteStatus newStatus = DeliveryRoute.RouteStatus.valueOf(deliveryRouteStatus);
            Delivery delivery = findDeliveryById(deliveryRoute.getDeliveryId());

            updateRouteStatus(deliveryRoute, newStatus);
            String slackContent = generateSlackContent(deliveryRoute, newStatus);
            sendSlackNotification(delivery.getReceiverEmail(), slackContent);

            return new RouteResDto(deliveryRoute);
        } catch (BusinessLogicException e) {
            log.error("##### BusinessLogicException ##### "+e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("##### Exception ##### "+e.getMessage());
            throw new BusinessLogicException(ExceptionCode.INVALID_PARAMETER);
        }
    }

    private DeliveryRoute findDeliveryRouteById(String deliveryRouteId) {
        return deliveryRouteRepository.findById(UUID.fromString(deliveryRouteId))
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.INVALID_PARAMETER));
    }

    private Delivery findDeliveryById(UUID deliveryId) {
        return deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.DELIVERY_NOT_FOUND));
    }

    private void updateRouteStatus(DeliveryRoute deliveryRoute, DeliveryRoute.RouteStatus newStatus) {
        LocalDateTime now = LocalDateTime.now();

        if (newStatus == DeliveryRoute.RouteStatus.DELIVERING) {
            if (deliveryRoute.getSequence() == 1 || deliveryRoute.getStartAt() == null) {
                updateDeliveryStatus(deliveryRoute.getDeliveryId(), Delivery.DeliveryStatus.DELIVERING);
                deliveryRoute.setStartAt(now);
            }
        } else if (newStatus == DeliveryRoute.RouteStatus.ARRIVED) {
            if (isLastRoute(deliveryRoute)) {
                updateDeliveryStatus(deliveryRoute.getDeliveryId(), Delivery.DeliveryStatus.DELIVERED);
                deliveryRoute.setEndAt(now);
                calculateAndSetActualTime(deliveryRoute, now);
            } else {
                deliveryRoute.setEndAt(now);
            }
        }
        deliveryRoute.setStatus(newStatus);
        deliveryRouteRepository.save(deliveryRoute);
    }


    private void calculateAndSetActualTime(DeliveryRoute deliveryRoute, LocalDateTime endTime) {
        if (deliveryRoute.getStartAt() != null) {
            Duration duration = Duration.between(deliveryRoute.getStartAt(), endTime);
            double actualTimeInMinutes = duration.toSeconds() / 60.0;
            deliveryRoute.setActualTime(actualTimeInMinutes);
        }
    }

    private String generateSlackContent(DeliveryRoute deliveryRoute, DeliveryRoute.RouteStatus newStatus) {
        if (deliveryRoute.getSequence() == 1 && newStatus == DeliveryRoute.RouteStatus.DELIVERING) {
            return SlackTemplate.startDelivery(deliveryRoute.getDeliveryId().toString(), newStatus.toString());
        } else if (isLastRoute(deliveryRoute) && newStatus == DeliveryRoute.RouteStatus.ARRIVED) {
            return SlackTemplate.endDelivery(deliveryRoute.getDeliveryId().toString(), newStatus.toString());
        } else if (newStatus == DeliveryRoute.RouteStatus.ARRIVED) {
            return SlackTemplate.arrivedAtHub(deliveryRoute.getDeliveryId().toString(), newStatus.toString(), deliveryRoute.getEndPoint());
        } else {
            return SlackTemplate.updateDeliveryStatus(deliveryRoute.getDeliveryId().toString(), newStatus.toString());
        }
    }

    @Async
    public void sendSlackNotification(String receiverEmail, String content) {
        SlackRequestDto slackRequestDto = SlackRequestDto.builder()
                .email(receiverEmail)
                .content(content)
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonMessage = objectMapper.writeValueAsString(slackRequestDto);
            kafkaTemplate.send("delivery-status-update", jsonMessage);
        } catch (JsonProcessingException e) {
            throw new BusinessLogicException(ExceptionCode.INVALID_PARAMETER);
        } catch (Exception e) {
            throw new BusinessLogicException(ExceptionCode.SLACK_NOTIFICATION_FAILED);
        }
    }

    private void updateDeliveryStatus(UUID deliveryId, Delivery.DeliveryStatus status) {
        Delivery delivery = findDeliveryById(deliveryId);
        delivery.setDeliveryStatus(status);
        deliveryRepository.save(delivery);
    }

    private boolean isLastRoute(DeliveryRoute route) {
        int totalRoutes = deliveryRouteRepository.countByDeliveryId(route.getDeliveryId());
        return totalRoutes == route.getSequence();
    }
}
