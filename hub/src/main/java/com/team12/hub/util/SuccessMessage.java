package com.team12.hub.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessMessage {
    CREATE_HUB(HttpStatus.OK, "허브 생성 성공"),
    UPDATE_HUB(HttpStatus.OK, "허브 수정 성공"),
    DELETE_HUB(HttpStatus.OK, "허브 삭제 성공"),
    GET_HUB(HttpStatus.OK, "허브 상세 조회 성공"),
    GET_HUBS(HttpStatus.OK, "허브 전체 조회 및 검색 성공");


    private final HttpStatus httpStatus;
    private final String message;
}
