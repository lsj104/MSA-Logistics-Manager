package com.team12.company_product.company.dto.request;

import com.team12.company_product.company.domain.CompanyType;

public record CreateCompanyRequestDto(
        String companyName,
        CompanyType companyType,
        String hubId,
        String address

) {

}
