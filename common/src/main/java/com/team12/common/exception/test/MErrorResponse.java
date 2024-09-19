package com.team12.common.exception.test;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MErrorResponse {
    private int status;
    private String message;

    @Builder
    protected MErrorResponse(final ErrorCode code) {
        this.status = code.getStatus();
        this.message = code.getMessage();
    }

    private MErrorResponse(String message, int status) {
        this.message = message;
        this.status = status;
    }

    public static MErrorResponse of(ErrorCode errorCode) {
        return new MErrorResponse( errorCode.getMessage(), errorCode.getStatus());
    }
}
