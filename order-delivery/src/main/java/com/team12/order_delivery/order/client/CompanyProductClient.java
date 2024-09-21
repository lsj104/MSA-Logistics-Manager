package com.team12.order_delivery.order.client;

import com.team12.common.dto.company.CompanyResponseDto;
import com.team12.common.exception.response.SuccessResponse;
import com.team12.common.dto.product.ProductResponseDto;
import com.team12.common.dto.product.UpdateProductQuantityRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "company-product")
public interface CompanyProductClient {

    @GetMapping("/api/companies/{companyId}")
    SuccessResponse<CompanyResponseDto> fetchCompanyById(
            @PathVariable("companyId") String companyId);

    @GetMapping("/api/products/{productId}")
    SuccessResponse<ProductResponseDto> fetchProductById(
            @PathVariable("productId") String productId);

    @PutMapping("/api/products/{productId}/reduce-quantity")
    void reduceProductQuantity(@PathVariable("productId") String productId,
            @RequestParam("quantity") Long quantity);

    @PutMapping("/api/products/{productId}/quantity")
    SuccessResponse<Void> updateProductQuantity(
            @PathVariable("productId") String productId,
            @RequestBody UpdateProductQuantityRequestDto requestDto);

}