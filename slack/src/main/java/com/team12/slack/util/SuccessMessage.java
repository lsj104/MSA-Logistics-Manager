package com.team12.slack.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessMessage {
    SEND_MESSAGE(HttpStatus.OK, "메시지 전송 성공"),
    DELETE_MESSAGE(HttpStatus.OK, "메시지 삭제 성공"),
    GET_MESSAGE(HttpStatus.OK, "메시지 조회 성공"),
    UPDATE_MESSAGE(HttpStatus.OK, "메시지 수정 성공"),
    ;


    private final HttpStatus httpStatus;
    private final String message;
}
