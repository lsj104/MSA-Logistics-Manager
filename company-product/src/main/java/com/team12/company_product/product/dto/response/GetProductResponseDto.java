package com.team12.company_product.product.dto.response;

import com.team12.company_product.product.domain.Product;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record GetProductResponseDto(
        String productId,
        String companyId,
        String hub_id,
        String productName,
        Boolean isDelete,
        LocalDateTime createdAt,
        Long createdBy,
        LocalDateTime updatedAt,
        Long updatedBy,
        LocalDateTime deletedAt,
        Long deletedBy
) {

    public static GetProductResponseDto from(Product product) {
        return GetProductResponseDto.builder()
                .productId(product.getProductId())
                .companyId(product.getCompany().getCompanyId())
                .hub_id(product.getHubId())
                .productName(product.getProductName())
                .isDelete(product.getIsDeleted())
                .createdAt(product.getCreatedAt())
                .createdBy(product.getCreatedBy())
                .updatedAt(product.getUpdatedAt())
                .updatedBy(product.getUpdatedBy())
                .deletedAt(product.getDeletedAt())
                .deletedBy(product.getDeletedBy())
                .build();
    }
}
