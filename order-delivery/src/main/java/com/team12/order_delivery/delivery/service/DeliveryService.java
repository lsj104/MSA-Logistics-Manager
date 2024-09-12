package com.team12.order_delivery.delivery.service;

import com.team12.common.customPage.CustomPageResponse;
import com.team12.common.exception.BusinessLogicException;
import com.team12.common.exception.ExceptionCode;
import com.team12.order_delivery.delivery.domain.Delivery;
import com.team12.order_delivery.delivery.dto.DeliveryReqDto;
import com.team12.order_delivery.delivery.dto.DeliveryResDto;
import com.team12.order_delivery.delivery.repository.DeliveryRespository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryRespository deliveryRespository;

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
            createDeliveryRoutes(delivery);
            return new DeliveryResDto(delivery);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new BusinessLogicException(ExceptionCode.INVALID_PARAMETER);
        }

    }

    private void createDeliveryRoutes(Delivery delivery) {

        // TODO : 허브 간 이동 경로 가져옴

        int sequence = 1;
        // TODO : 허브 간 이동 경로 저장


    }

    public DeliveryResDto getDelivery(String deliveryId) {
        try {
            return new DeliveryResDto(deliveryRespository.findById(UUID.fromString(deliveryId)).orElseThrow(() -> new IllegalArgumentException("배송 정보가 없습니다.")));
        } catch (Exception e) {
            log.info(e.getMessage());
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
}
