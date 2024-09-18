package com.team12.order_delivery.order.dto.request;

import lombok.Builder;

@Builder
public record CreateOrderRequestDto(
        String producerCompany,
        String receiverCompany,
        Long quantity,
        String address,
        String receiver,
        String receiverEmail,
        String productId
) {

}