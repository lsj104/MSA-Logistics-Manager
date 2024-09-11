package com.team12.company_product.company.dto;

import com.team12.company_product.company.domain.Company;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record UpdateCompanyResponseDto(

        String companyId,
        String companyName,
        String companyType,
        String hubId,
        String address,
        LocalDateTime updatedAt,
        Long updatedBy,
        Boolean isDelete
) {

    public static UpdateCompanyResponseDto from(Company company) {
        return UpdateCompanyResponseDto.builder()
                .companyId(company.getCompanyId())
                .companyName(company.getCompanyName())
                .companyType(builder().companyType)
                .hubId(company.getHubId())
                .address(company.getAddress())
                .isDelete(company.getIsDeleted())
                .updatedAt(company.getUpdatedAt())
                .updatedBy(company.getUpdatedBy())
                .build();
    }
}
