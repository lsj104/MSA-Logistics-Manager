package com.team12.company_product.company.dto.request;


public record UpdateCompanyRequestDto(

        String companyName,
        String companyType,
        String hubId,
        String address

) {

}
