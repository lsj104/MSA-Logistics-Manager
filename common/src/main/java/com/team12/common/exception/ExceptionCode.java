package com.team12.common.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {
    // 예시
    EXIST_PRODUCT(409, "존재하는 상품입니다."),
    NOT_EXIST_USER(1404, "존재하지 않는 회원입니다."),
    ALREADY_EXIST_USER(1403, "이미 존재하는 회원입니다.");


    private int status;
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
