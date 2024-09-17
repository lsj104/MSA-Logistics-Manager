package com.team12.order_delivery.order.dto.response;

import com.team12.order_delivery.order.domain.Order;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record CreateOrderResponseDto(

        UUID orderId,
        String producerCompany,
        String receiverCompany,
        Long quantity,
        UUID deliveryId,
        String deliveryStatus,
        Boolean isDelete,
        LocalDateTime createdAt,
        Long createdBy,
        String productId

) {

    public static CreateOrderResponseDto from(Order order) {
        return CreateOrderResponseDto.builder()
                .orderId(order.getOrderId())
                .producerCompany(order.getProducerCompanyId())
                .receiverCompany(order.getReceiverCompanyId())
                .quantity(order.getQuantity())
                .deliveryId(order.getDelivery().getId())
                .deliveryStatus(order.getDelivery().getDeliveryStatus().name())
                .isDelete(order.getIsDeleted())
                .createdAt(order.getCreatedAt())
                .createdBy(order.getCreatedBy())
                .productId(order.getProductId())
                .build();
    }
}