package com.team12.common.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {
    // 예시
    EXIST_PRODUCT(403, "존재하는 상품입니다.");


    private int status;
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
