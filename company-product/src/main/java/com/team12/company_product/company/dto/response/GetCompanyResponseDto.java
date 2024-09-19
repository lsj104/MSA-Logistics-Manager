package com.team12.company_product.company.dto.response;

import com.team12.company_product.company.domain.Company;
import com.team12.company_product.company.domain.CompanyType;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record GetCompanyResponseDto(
        String companyId,
        String companyName,
        CompanyType companyType,
        UUID hubId,
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
                .companyId(String.valueOf(company.getCompanyId()))
                .companyName(company.getCompanyName())
                .companyType(company.getCompanyType())
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
