package com.team12.common.exception.response;

import lombok.NonNull;

public interface CommonResponse {

    @NonNull
    String message();

    int resultCode();

}
