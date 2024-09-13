package com.team12.order_delivery.delivery.dto;

import com.team12.order_delivery.delivery.domain.Delivery;
import lombok.Data;

import java.util.UUID;

@Data
public class DeliveryResDto {
    private UUID deliveryId;
    private UUID orderId;
    private String deliveryStatus;
    private UUID fromHubId;
    private UUID toHubId;
    private String address;
    private String receiver;
    private String receiverEmail;

    public DeliveryResDto(Delivery delivery) {
        this.deliveryId = delivery.getId();
        this.orderId = delivery.getOrderId();
        this.deliveryStatus = delivery.getDeliveryStatus().name();
        this.fromHubId = delivery.getFromHubId();
        this.toHubId = delivery.getToHubId();
        this.address = delivery.getAddress();
        this.receiver = delivery.getReceiver();
        this.receiverEmail = delivery.getReceiverEmail();
    }
}
