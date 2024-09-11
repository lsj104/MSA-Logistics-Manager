package com.team12.company_product.product.controller;

import static com.team12.common.exception.response.SuccessResponse.success;
import static com.team12.company_product.product.message.SuccessMessage.CREATE_PRODUCT;
import static com.team12.company_product.product.message.SuccessMessage.GET_PRODUCT;

import com.team12.common.exception.response.SuccessResponse;
import com.team12.company_product.product.dto.CreateProductRequestDto;
import com.team12.company_product.product.dto.CreateProductResponseDto;
import com.team12.company_product.product.dto.GetProductResponseDto;
import com.team12.company_product.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "상품", description = "상품 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "상품 생성")
    @PostMapping
    public ResponseEntity<SuccessResponse<CreateProductResponseDto>> createProduct(
            @RequestBody CreateProductRequestDto requestDto) {

        return ResponseEntity.status(CREATE_PRODUCT.getHttpStatus())
                .body(success(CREATE_PRODUCT.getHttpStatus().value(),
                        CREATE_PRODUCT.getMessage(),
                        productService.createProduct(requestDto)));
    }

    @Operation(summary = "상품 상세 조회")
    @GetMapping("/{productId}")
    public ResponseEntity<SuccessResponse<GetProductResponseDto>> getProduct(
            @PathVariable("productId") String productId) {
        GetProductResponseDto responseDto = productService.getProduct(productId);

        return ResponseEntity.status(GET_PRODUCT.getHttpStatus())
                .body(success(GET_PRODUCT.getHttpStatus().value(),
                        GET_PRODUCT.getMessage(), responseDto));
    }
}
