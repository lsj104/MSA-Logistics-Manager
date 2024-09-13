package com.team12.company_product.company.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessMessage {
    CREATE_COMPANY(HttpStatus.CREATED, "업체 생성이 완료되었습니다."),
    GET_COMPANY(HttpStatus.OK, "업체 조회가 완료되었습니다."),
    UPDATE_COMPANY(HttpStatus.OK, "업체 수정이 완료되었습니다."),
    DELETE_COMPANY(HttpStatus.OK, "업체 삭제가 완료되었습니다."),
    SEARCH_COMPANY(HttpStatus.OK, "업체 검색이 완료되었습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
