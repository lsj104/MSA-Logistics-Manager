package com.team12.order_delivery.order.dto.request;

public record UpdateOrderRequestDto(
        String producerCompany,
        String receiverCompany,
        Long quantity,
        String address,
        String receiver,
        String receiverEmail

) {

}
