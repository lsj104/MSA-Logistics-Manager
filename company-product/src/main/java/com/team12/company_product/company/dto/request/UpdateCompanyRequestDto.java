package com.team12.company_product.company.dto.request;


import com.team12.company_product.company.domain.CompanyType;
import java.util.UUID;

public record UpdateCompanyRequestDto(

        String companyName,
        CompanyType companyType,
        UUID hubId,
        String address

) {

}
