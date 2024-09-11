package com.team12.company_product.product.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessMessage {
    CREATE_PRODUCT(HttpStatus.CREATED, "상품 생성이 완료되었습니다."),
    GET_PRODUCT(HttpStatus.OK, "상품 조회가 완료되었습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
