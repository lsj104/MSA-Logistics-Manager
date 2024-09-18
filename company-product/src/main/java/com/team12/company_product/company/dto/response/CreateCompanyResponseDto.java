package com.team12.company_product.company.dto.response;

import com.team12.company_product.company.domain.Company;
import com.team12.company_product.company.domain.CompanyType;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record CreateCompanyResponseDto(
        String companyId,
        String companyName,
        CompanyType companyType,
        String hubId,
        String address,
        Boolean isDelete,
        LocalDateTime createdAt,
        Long createdBy
) {

    public static CreateCompanyResponseDto from(Company company) {
        return CreateCompanyResponseDto.builder()
                .companyId(String.valueOf(company.getCompanyId()))
                .companyName(company.getCompanyName())
                .companyType(company.getCompanyType())
                .hubId(company.getHubId())
                .address(company.getAddress())
                .isDelete(company.getIsDeleted())
                .createdAt(company.getCreatedAt())
                .createdBy(company.getCreatedBy())
                .build();
    }

}
