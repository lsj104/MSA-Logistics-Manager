package com.team12.company_product.product.service;

import com.team12.company_product.company.dto.CreateCompanyRequestDto;
import com.team12.company_product.product.dto.CreateProductRequestDto;
import com.team12.company_product.product.dto.CreateProductResponseDto;
import com.team12.company_product.product.dto.GetProductResponseDto;

public interface ProductService {

    // 상품 생성
    CreateProductResponseDto createProduct(CreateProductRequestDto requestDto);

    // 상품 조회
    GetProductResponseDto getProduct(String productId);
}
