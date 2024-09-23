package com.team12.company_product.product.dto.request;

import java.util.UUID;

public record UpdateProductRequestDto(
        String productName,
        UUID hubId,
        String companyId,
        Long quantity
) {


}
