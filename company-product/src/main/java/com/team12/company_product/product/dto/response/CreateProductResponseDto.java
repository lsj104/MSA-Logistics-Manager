package com.team12.company_product.product.dto.response;

import com.team12.company_product.product.domain.Product;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record CreateProductResponseDto(

        String productId,
        String companyId,
        UUID hubId,
        String productName,
        Boolean isDelete,
        LocalDateTime createdAt,
        Long createdBy,
        Long quantity
) {

    public static CreateProductResponseDto from(Product product) {
        return CreateProductResponseDto.builder()
                .productId(product.getProductId())
                .companyId(product.getCompany().getCompanyId())
                .hubId(product.getHubId())
                .productName(product.getProductName())
                .isDelete(product.getIsDeleted())
                .createdAt(product.getCreatedAt())
                .createdBy(product.getCreatedBy())
                .quantity(product.getQuantity())
                .build();
    }
}
