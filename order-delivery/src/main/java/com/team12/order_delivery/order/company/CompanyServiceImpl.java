package com.team12.order_delivery.order.company;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyProductClient companyProductClient;

    @Override
    public CompanyResponseDto getCompany(String companyId) {
        return companyProductClient.fetchCompanyById(companyId).data();
    }
}