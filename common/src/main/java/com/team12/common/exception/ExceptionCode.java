package com.team12.common.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {

    /*
    공통으로 사용하는 exception 9로 시작
     */
    INVALID_PARAMETER(9000, "잘못된 요청입니다."),


    /*
    USER 1로 시작
    */
    // USER 1100~
    USER_NOT_FOUND(1101, "해당 사용자가 존재하지 않습니다."),

    /*
    COMPANY_PRODUCT Module 2로 시작
     */
    // - COMPANY 2100~
    COMPANY_NOT_FOUND(2101, "해당 업체가 존재하지 않습니다."),

    // - PRODUCT 2200~
    PRODUCT_NOT_FOUND(2201, "해당 상품이 존재하지 않습니다."),

    /*
    HUB Module 3으로 시작
     */
    // HUB 3100~
    HUB_NOT_FOUND(3101, "해당 허브가 존재하지 않습니다."),

    // HUB_PATH 3200~
    HUB_PATH_NOT_FOUND(3201, "해당 허브 간 이동이 존재하지 않습니다."),
    FROM_HUB_NOT_FOUND(3202, "입력하신 출발 허브가 존재하지 않습니다."),
    TO_HUB_NOT_FOUND(3203, "입력하신 도착 허브가 존재하지 않습니다."),

    // MANAGER 3300~
    MANAGER_NOT_FOUND(3301, "해당 담당자가 존재하지 않습니다."),

    /*
    ORDER_DELIVERY Module 4로 시작
    */
    // ORDER 4100~
    ORDER_NOT_FOUND(4101, "해당 주문 내역이 존재하지 않습니다."),

    // DELIVERY 4200~
    DELIVERY_NOT_FOUND(4201, "해당 배송 내역이 존재하지 않습니다."),

    // DELIVERY_ROUTE 4300~
    DELIVERY_ROUTE_NOT_FOUND(4301, "해당 배송 경로 내역이 존재하지 않습니다."),

    /*
    SLACK Module 5로 시작
     */
    // SLACK 5100~
    SLACK_NOT_FOUND(5101, "해당 슬랙이 존재하지 않습니다."),
    SLACK_NOTIFICATION_FAILED(5102, "슬랙 알림 전송에 실패하였습니다."),;



    // 예시
    // EXIST_PRODUCT(403, "존재하는 상품입니다.");
    // DELIVERY_NOT_FOUND(404, "배송 정보를 찾을 수 없습니다."),
    // USER_NOT_FOUND(404, "사용자 정보를 찾을 수 없습니다.");


    private int status;
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}