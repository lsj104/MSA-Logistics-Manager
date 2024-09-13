package com.team12.company_product.company.dto;

import com.team12.company_product.company.domain.Company;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record GetCompanyResponseDto(
        String companyId,
        String companyName,
        String companyType,
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

    public static GetCompanyResponseDto from(Company company) {
        return GetCompanyResponseDto.builder()
                .companyId(company.getCompanyId())
                .companyName(company.getCompanyName())
                .companyType(builder().companyType)
                .hubId(company.getHubId())
                .address(company.getAddress())
                .isDelete(company.getIsDeleted())
                .createdAt(company.getCreatedAt())
                .createdBy(company.getCreatedBy())
                .updatedAt(company.getUpdatedAt())
                .updatedBy(company.getUpdatedBy())
                .build();
    }

}
