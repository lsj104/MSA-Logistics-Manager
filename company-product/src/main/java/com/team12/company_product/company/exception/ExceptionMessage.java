package com.team12.company_product.company.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessage {
    COMPANY_NOT_FOUND(HttpStatus.NOT_FOUND, "조회한 업체가 존재하지 않습니다."),
    KEYWORD_EMPTY(HttpStatus.BAD_REQUEST, "키워드는 최소 1자 이상 작성해 주셔야 합니다."),
    KEYWORD_NOT_PROVIDED(HttpStatus.NOT_FOUND, "검색한 키워드가 존재하지 않습니다."),
    HUB_NOT_FOUND(HttpStatus.NOT_FOUND, "허브를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
