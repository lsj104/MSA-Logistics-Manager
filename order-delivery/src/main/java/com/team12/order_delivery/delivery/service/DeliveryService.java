package com.team12.order_delivery.delivery.service;

import com.team12.order_delivery.delivery.domain.Delivery;
import com.team12.order_delivery.delivery.dto.DeliveryReqDto;
import com.team12.order_delivery.delivery.dto.DeliveryResDto;
import com.team12.order_delivery.delivery.repository.DeliveryRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryRespository deliveryRespository;

    public String createDelivery(DeliveryReqDto deliveryReqDto) {
        Delivery delivery = new Delivery(deliveryReqDto);
        deliveryRespository.save(delivery);

        createDeliveryRoutes(delivery);
        return "배송 생성 완료";

    }

    private void createDeliveryRoutes(Delivery delivery) {

        // TODO : 허브 간 이동 경로 가져옴

        int sequence = 1;
        // TODO : 허브 간 이동 경로 저장


    }

    public DeliveryResDto getDelivery(String deliveryId) {
        Delivery delivery = deliveryRespository.findById(UUID.fromString(deliveryId)).orElseThrow(() -> new IllegalArgumentException("배송 정보가 없습니다."));
        return new DeliveryResDto(delivery);
    }


    public Page<DeliveryResDto> getAllDelivery(Pageable pageable) {
        return deliveryRespository.findAll(pageable).map(DeliveryResDto::new);
    }

    public String updateDelivery(String deliveryId, DeliveryReqDto deliveryReqDto) {
        Delivery delivery = deliveryRespository.findById(UUID.fromString(deliveryId)).orElseThrow(() -> new IllegalArgumentException("배송 정보가 없습니다."));
        delivery.update(deliveryReqDto);
        deliveryRespository.save(delivery);
        return "배송 정보 수정 완료";
    }

    public String deleteDelivery(String deliveryId) {
        deliveryRespository.deleteById(UUID.fromString(deliveryId));
        return "배송 정보 삭제 완료";
    }
}
