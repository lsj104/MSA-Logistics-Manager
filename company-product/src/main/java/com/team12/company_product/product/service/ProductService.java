package com.team12.company_product.product.service;

import com.team12.company_product.product.domain.Product;
import com.team12.company_product.product.dto.request.CreateProductRequestDto;
import com.team12.company_product.product.dto.request.UpdateProductRequestDto;
import com.team12.company_product.product.dto.response.CreateProductResponseDto;
import com.team12.company_product.product.dto.response.DeleteProductResponseDto;
import com.team12.company_product.product.dto.response.GetProductResponseDto;
import com.team12.company_product.product.dto.response.UpdateProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    // 상품 생성
    CreateProductResponseDto createProduct(CreateProductRequestDto requestDto);

    // 상품 조회
    GetProductResponseDto getProduct(String productId);

    // 모든 상품 조회
    Page<GetProductResponseDto> getAllProduct(Pageable pageable);

    // 상품 수정
    UpdateProductResponseDto updateProduct(UpdateProductRequestDto requestDto, String productId);

    // 상품 삭제
    DeleteProductResponseDto deleteProduct(String productId, Long userId);

    // ID로 상품 검색
    Product findById(String productId);

    // 상품 검색
    Page<GetProductResponseDto> searchProduct(String keyword, Pageable pageable);

    // 상품 수량 차감
    void reduceProductQuantity(String productId, Long quantity);

    void updateProductQuantity(String productId, Long aLong);
}
