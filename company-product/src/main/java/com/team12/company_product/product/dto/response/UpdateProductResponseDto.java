package com.team12.company_product.product.dto.response;

import com.team12.company_product.product.domain.Product;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record UpdateProductResponseDto(
        String productName,
        UUID hub_id,
        String companyId,
        LocalDateTime updatedAt,
        Long updatedBy,
        Boolean isDelete,
        Long quantity

) {

    public static UpdateProductResponseDto from(Product product) {
        return UpdateProductResponseDto.builder()
                .productName(product.getProductName())
                .hub_id(product.getHubId())
                .companyId(product.getCompany().getCompanyId())
                .updatedAt(product.getUpdatedAt())
                .updatedBy(product.getUpdatedBy())
                .quantity(product.getQuantity())
                .isDelete(product.getIsDeleted())
                .build();
    }
}
