package com.team12.order_delivery.order.dto.response;

import com.team12.order_delivery.delivery.domain.Delivery;
import com.team12.order_delivery.order.domain.Order;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record UpdateOrderResponseDto(
        String producerCompany,
        String receiverCompany,
        Long quantity,
        String address,
        String receiver,
        String receiverEmail,
        LocalDateTime updatedAt,
        Long updatedBy,
        Boolean isDelete
) {

    public static UpdateOrderResponseDto from(Order order, Delivery delivery) {
        return UpdateOrderResponseDto.builder()
                .producerCompany(order.getProducerCompanyId())
                .receiverCompany(order.getReceiverCompanyId())
                .quantity(order.getQuantity())
                .address(delivery.getAddress())
                .receiver(delivery.getReceiver())
                .receiverEmail(delivery.getReceiverEmail())
                .updatedAt(order.getUpdatedAt())
                .updatedBy(order.getUpdatedBy())
                .isDelete(order.getIsDeleted())
                .build();
    }
}
