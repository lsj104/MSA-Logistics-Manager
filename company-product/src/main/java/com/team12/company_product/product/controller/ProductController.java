package com.team12.company_product.product.controller;

import static com.team12.common.exception.response.SuccessResponse.success;
import static com.team12.company_product.product.message.SuccessMessage.CREATE_PRODUCT;
import static com.team12.company_product.product.message.SuccessMessage.DELETE_PRODUCT;
import static com.team12.company_product.product.message.SuccessMessage.GET_PRODUCT;
import static com.team12.company_product.product.message.SuccessMessage.SEARCH_PRODUCT;
import static com.team12.company_product.product.message.SuccessMessage.UPDATE_PRODUCT;

import com.team12.common.customPage.CustomPageResponse;
import com.team12.common.exception.response.SuccessResponse;
import com.team12.company_product.product.dto.request.CreateProductRequestDto;
import com.team12.company_product.product.dto.request.UpdateProductQuantityRequestDto;
import com.team12.company_product.product.dto.request.UpdateProductRequestDto;
import com.team12.company_product.product.dto.response.CreateProductResponseDto;
import com.team12.company_product.product.dto.response.DeleteProductResponseDto;
import com.team12.company_product.product.dto.response.GetProductResponseDto;
import com.team12.company_product.product.dto.response.UpdateProductResponseDto;
import com.team12.company_product.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody CreateProductRequestDto requestDto) {

        return ResponseEntity.status(CREATE_PRODUCT.getHttpStatus())
                .body(success(CREATE_PRODUCT.getHttpStatus().value(),
                        CREATE_PRODUCT.getMessage(),
                        productService.createProduct(requestDto, userId)));
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

    @Operation(summary = "모든 상품 조회")
    @GetMapping
    public ResponseEntity<SuccessResponse<CustomPageResponse<GetProductResponseDto>>> getAllProduct(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<GetProductResponseDto> responseDto = productService.getAllProduct(pageable);

        CustomPageResponse<GetProductResponseDto> customResponse = new CustomPageResponse<>(
                responseDto);

        String message = String.format("%d개의 상품 조회 완료되었습니다.", responseDto.getTotalElements());

        return ResponseEntity.status(GET_PRODUCT.getHttpStatus())
                .body(success(GET_PRODUCT.getHttpStatus().value(), message, customResponse));
    }

    @Operation(summary = "상품 수정")
    @PutMapping("/{productId}")
    public ResponseEntity<SuccessResponse<UpdateProductResponseDto>> updateProduct(
            @PathVariable("productId") String productId,
            @RequestBody UpdateProductRequestDto requestDto,
            @RequestHeader("X-User-Id") Long userId) {

        UpdateProductResponseDto responseDto = productService.updateProduct(requestDto, productId,
                userId);

        return ResponseEntity.status(UPDATE_PRODUCT.getHttpStatus())
                .body(success(UPDATE_PRODUCT.getHttpStatus().value(),
                        UPDATE_PRODUCT.getMessage(), responseDto));

    }

    @Operation(summary = "상품 삭제")
    @DeleteMapping("/{productId}")
    public ResponseEntity<SuccessResponse<DeleteProductResponseDto>> deleteProduct(
            @PathVariable("productId") String productId,
            @RequestHeader("X-User-Id") Long userId
    ) {
        DeleteProductResponseDto responseDto = productService.deleteProduct(productId, userId);

        return ResponseEntity.status(DELETE_PRODUCT.getHttpStatus())
                .body(success(DELETE_PRODUCT.getHttpStatus().value(),
                        DELETE_PRODUCT.getMessage(), responseDto));

    }

    @Operation(summary = "상품 검색", description = "상품 이름으로 검색, ID로 검색할 수 있는 API입니다.")
    @GetMapping("/search")
    public ResponseEntity<SuccessResponse<CustomPageResponse<GetProductResponseDto>>> searchProduct(
            @RequestParam("keyword") String keyword,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<GetProductResponseDto> responseDto = productService.searchProduct(keyword, pageable);

        CustomPageResponse<GetProductResponseDto> customResponse = new CustomPageResponse<>(
                responseDto);

        return ResponseEntity.status(SEARCH_PRODUCT.getHttpStatus())
                .body(success(SEARCH_PRODUCT.getHttpStatus().value(),
                        SEARCH_PRODUCT.getMessage(), customResponse));
    }

    @Operation(summary = "상품 수량 차감")
    @PutMapping("/{productId}/reduce-quantity")
    public ResponseEntity<Void> reduceQuantity(@PathVariable String productId,
            @RequestParam Long quantity) {
        productService.reduceProductQuantity(productId, quantity);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{productId}/quantity")
    public ResponseEntity<SuccessResponse<Void>> updateProductQuantity(
            @PathVariable("productId") String productId,
            @RequestBody UpdateProductQuantityRequestDto requestDto) {

        productService.updateProductQuantity(productId, requestDto.newQuantity());

        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse<>(HttpStatus.OK.value(),
                        "Product quantity updated successfully", null));
    }


}
