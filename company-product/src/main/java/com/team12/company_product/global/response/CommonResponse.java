package com.team12.company_product.global.response;

import lombok.NonNull;

public interface CommonResponse {

    @NonNull
    String message();

    int resultCode();

}
