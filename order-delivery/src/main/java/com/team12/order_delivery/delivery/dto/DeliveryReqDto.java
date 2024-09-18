package com.team12.order_delivery.delivery.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class DeliveryReqDto {
    private UUID orderId;
    private UUID departmentId;
    private UUID arrivalHubId;
    private String address;
    private String receiver;
    private String receiverEmail;

}

