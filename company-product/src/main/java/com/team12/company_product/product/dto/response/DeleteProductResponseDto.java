package com.team12.company_product.product.dto.response;

import com.team12.company_product.product.domain.Product;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record DeleteProductResponseDto(
        Boolean isDelete,
        LocalDateTime deletedAt,
        Long deletedBy
) {

    public static DeleteProductResponseDto from(Product product) {
        return DeleteProductResponseDto.builder()
                .isDelete(product.getIsDeleted())
                .deletedAt(product.getDeletedAt())
                .deletedBy(product.getDeletedBy())
                .build();

    }
}
