package com.team12.order_delivery.delivery.service;

import com.team12.order_delivery.delivery.domain.Delivery;
import com.team12.order_delivery.delivery.dto.DeliveryReqDto;
import com.team12.order_delivery.delivery.repository.DeliveryRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
