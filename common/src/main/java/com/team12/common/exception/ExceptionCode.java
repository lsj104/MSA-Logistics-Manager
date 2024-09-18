package com.team12.common.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {
    // 예시
    EXIST_PRODUCT(409, "존재하는 상품입니다."),
    NOT_EXIST_USER(1404, "존재하지 않는 회원입니다."),
    ALREADY_EXIST_USER(1403, "이미 존재하는 회원입니다."),
    NOT_PROVIDE_LANGUAGE(1403,"올바른 값을 입력해주세요."),
    DELETED_USER(1404, "이미 삭제된 회원입니다."),

    INVALID_PARAMETER(400, "잘못된 요청입니다."),
    DELIVERY_NOT_FOUND(404, "배송 정보를 찾을 수 없습니다."),
    USER_NOT_FOUND(404, "사용자 정보를 찾을 수 없습니다.");



    private int status;
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
