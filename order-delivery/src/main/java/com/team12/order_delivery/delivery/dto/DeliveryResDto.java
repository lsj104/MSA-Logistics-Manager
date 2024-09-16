package com.team12.order_delivery.delivery.dto;

import com.team12.order_delivery.delivery.domain.Delivery;
import lombok.Data;

import java.util.UUID;

@Data
public class DeliveryResDto {
    private UUID deliveryId;
    private UUID orderId;
    private String deliveryStatus;
    private UUID departmentId;
    private UUID arrivalHubId;
    private String address;
    private String receiver;
    private String receiverEmail;

    public DeliveryResDto(Delivery delivery) {
        this.deliveryId = delivery.getId();
        this.orderId = delivery.getOrderId();
        this.deliveryStatus = delivery.getDeliveryStatus().name();
        this.departmentId = delivery.getDepartmentId();
        this.arrivalHubId = delivery.getArrivalHubId();
        this.address = delivery.getAddress();
        this.receiver = delivery.getReceiver();
        this.receiverEmail = delivery.getReceiverEmail();
    }
}
