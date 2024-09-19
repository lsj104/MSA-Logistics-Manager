package com.team12.order_delivery.order.dto.response;

import com.team12.order_delivery.order.domain.Order;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record DeleteOrderResponseDto(
        Boolean isDelete,
        LocalDateTime deletedAt,
        Long deletedBy
) {

    public static DeleteOrderResponseDto from(Order order) {
        return DeleteOrderResponseDto.builder()
                .isDelete(order.getIsDeleted())
                .deletedAt(order.getDeletedAt())
                .deletedBy(order.getDeletedBy())
                .build();
    }

}
