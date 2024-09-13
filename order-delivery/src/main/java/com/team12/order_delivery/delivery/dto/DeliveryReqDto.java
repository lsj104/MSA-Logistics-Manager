package com.team12.order_delivery.delivery.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class DeliveryReqDto {
    private UUID orderId;
    private UUID fromHubId;
    private UUID toHubId;
    private String address;
    private String receiver;
    private String receiverEmail;

}

