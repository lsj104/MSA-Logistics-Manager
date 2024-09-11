package com.team12.company_product.company.dto;

import com.team12.company_product.company.domain.CompanyType;

public record CreateCompanyRequestDto(
        String companyName,
        CompanyType companyType,
        String hub_id,
        String address

) {

}
