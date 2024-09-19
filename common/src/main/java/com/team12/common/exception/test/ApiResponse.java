package com.team12.common.exception.test;

import lombok.Builder;
import lombok.Getter;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;

@Getter
public class ApiResponse<T> {
    private int resultCode;
    private String resultMessage;

    @Builder
    public ApiResponse(final int resultCode, final String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }
}
