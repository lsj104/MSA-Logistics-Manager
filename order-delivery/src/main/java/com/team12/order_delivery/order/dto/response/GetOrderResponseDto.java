package com.team12.order_delivery.order.dto.response;

import com.team12.order_delivery.order.domain.Order;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record GetOrderResponseDto(
        UUID orderId,
        String producerCompany,
        String receiverCompany,
        Long quantity,
        UUID deliveryId,
        String deliveryStatus,
        Boolean isDelete,
        LocalDateTime createdAt,
        Long createdBy,
        String productId,
        LocalDateTime updatedAt,
        Long updatedBy,
        LocalDateTime deletedAt,
        Long deletedBy
) {

    public static GetOrderResponseDto from(Order order) {
        return GetOrderResponseDto.builder()
                .orderId(order.getOrderId())
                .producerCompany(order.getProducerCompanyId())
                .receiverCompany(order.getReceiverCompanyId())
                .quantity(order.getQuantity())
                .deliveryId(order.getDelivery().getId())
                .deliveryStatus(order.getDelivery().getDeliveryStatus().name())
                .isDelete(order.getIsDeleted())
                .createdAt(order.getCreatedAt())
                .productId(order.getProductId())
                .updatedAt(order.getUpdatedAt())
                .updatedBy(order.getUpdatedBy())
                .deletedAt(order.getDeletedAt())
                .deletedBy(order.getDeletedBy())
                .build();
    }
}
