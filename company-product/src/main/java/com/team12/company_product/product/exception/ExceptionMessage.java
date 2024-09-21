package com.team12.company_product.product.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessage {
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "조회한 상품이 존재하지 않습니다."),
    KEYWORD_EMPTY(HttpStatus.BAD_REQUEST, "키워드는 최소 1자 이상 작성해 주셔야 합니다."),
    KEYWORD_NOT_PROVIDED(HttpStatus.NOT_FOUND, "검색한 키워드가 존재하지 않습니다."),
    INSUFFICIENT_PRODUCT_QUANTITY(HttpStatus.BAD_REQUEST, "상품 수량이 부족합니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
