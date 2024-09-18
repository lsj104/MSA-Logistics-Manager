package com.team12.order_delivery.order.company;

import com.team12.common.exception.response.SuccessResponse;
import com.team12.order_delivery.order.product.ProductResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientProperties.FeignClientConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "company-product")
public interface CompanyProductClient {

    @GetMapping("/api/companies/{companyId}")
    SuccessResponse<CompanyResponseDto> fetchCompanyById(
            @PathVariable("companyId") String companyId);

    @GetMapping("/api/products/{productId}")
    SuccessResponse<ProductResponseDto> fetchProductById(
            @PathVariable("productId") String productId);

}