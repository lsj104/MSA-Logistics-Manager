package com.team12.order_delivery.order.company;


import java.time.LocalDateTime;

public record CompanyResponseDto(
        String companyId,
        String companyName,
        CompanyType companyType,
        String hubId,
        String address,
        Boolean isDelete,
        LocalDateTime createdAt,
        Long createdBy,
        LocalDateTime updatedAt,
        Long updatedBy,
        LocalDateTime deletedAt,
        Long deletedBy
) {

}
