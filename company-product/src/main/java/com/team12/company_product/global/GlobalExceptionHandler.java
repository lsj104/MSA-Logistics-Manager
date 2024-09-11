package com.team12.company_product.global;

import com.team12.company_product.company.exception.CompanyException;
import com.team12.company_product.global.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.team12.company_product.global.response.ExceptionResponse.of;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CompanyException.class)
    public ResponseEntity<ExceptionResponse> handleCompanyException(CompanyException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(of(e.getMessage(), e.getHttpStatus().value()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(of(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST.value()));
    }
}