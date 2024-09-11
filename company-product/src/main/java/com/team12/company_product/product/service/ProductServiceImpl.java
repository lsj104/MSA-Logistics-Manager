package com.team12.company_product.product.service;


import com.team12.company_product.product.domain.Product;
import com.team12.company_product.product.dto.CreateProductRequestDto;
import com.team12.company_product.product.dto.CreateProductResponseDto;
import com.team12.company_product.product.dto.GetProductResponseDto;
import com.team12.company_product.product.exception.ExceptionMessage;
import com.team12.company_product.product.exception.ProductException;
import com.team12.company_product.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;


    // 상품 생성
    @Transactional
    public CreateProductResponseDto createProduct(CreateProductRequestDto requestDto) {

        Product product = Product.of(requestDto);
        productRepository.save(product);

        return CreateProductResponseDto.from(product);
    }

    // 상품 상세 조회
    @Transactional(readOnly = true)
    public GetProductResponseDto getProduct(String productId) {

        Product product = findById(productId);
        return GetProductResponseDto.from(product);
    }

    @Transactional
    public Product findById(String productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductException(ExceptionMessage.PRODUCT_NOT_FOUND));
    }
}
