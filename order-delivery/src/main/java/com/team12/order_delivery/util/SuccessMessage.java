package com.team12.order_delivery.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessMessage {
    CREATE_DELIVERY(HttpStatus.CREATED, "배송 정보 생성이 완료되었습니다."),
    GET_DELIVERY(HttpStatus.OK, "배송 정보 조회가 완료되었습니다."),
    UPDATE_DELIVERY(HttpStatus.OK, "배송 정보 수정이 완료되었습니다."),
    DELETE_DELIVERY(HttpStatus.OK, "배송 정보 삭제가 완료되었습니다."),
    CREATE_DELIVERY_ROUTE(HttpStatus.CREATED, "배송 경로 생성이 완료되었습니다."),
    GET_DELIVERY_ROUTE(HttpStatus.OK, "배송 경로 조회가 완료되었습니다."),
    UPDATE_DELIVERY_ROUTE(HttpStatus.OK, "배송 경로 수정이 완료되었습니다."),
    DELETE_DELIVERY_ROUTE(HttpStatus.OK, "배송 경로 삭제가 완료되었습니다.");


    private final HttpStatus httpStatus;
    private final String message;
}
