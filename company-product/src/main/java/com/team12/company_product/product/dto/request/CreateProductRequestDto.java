package com.team12.company_product.product.dto.request;

import java.util.UUID;

public record CreateProductRequestDto(
        String companyId,
        UUID hubId,
        String productName,
        Long quantity

) {

}
