package com.team12.common.exception.test;

import lombok.Getter;

@Getter
public enum ErrorCode {

    EXIST_PRODUCT( "존재하는 상품입니다.", 409);

    private final String message;
    private final int status;

    ErrorCode(String message, int code) {
        this.message = message;
        this.status = code;
    }
}
