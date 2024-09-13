package com.team12.company_product.company.dto;

import com.team12.company_product.company.domain.Company;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record DeleteCompanyResponseDto(
        Boolean isDelete,
        LocalDateTime deletedAt,
        Long deletedBy
) {

    public static DeleteCompanyResponseDto from(Company company) {
        return DeleteCompanyResponseDto.builder()
                .isDelete(company.getIsDeleted())
                .deletedAt(company.getDeletedAt())
                .deletedBy(company.getDeletedBy())
                .build();
    }

}
