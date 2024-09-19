package com.team12.common.exception.test;

import com.team12.common.exception.GlobalExceptionAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


//@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionAdvice.class);

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<MErrorResponse> handleBusinessException(BusinessException e) {
        log.error("Business logic exception", e);

        // Ensure e and e.getErrorCode() are not null
        ErrorCode errorCode = e.getErrorCode();
        if (errorCode == null) {
            errorCode = ErrorCode.EXIST_PRODUCT; // Define a default error code if needed
        }

        MErrorResponse response = MErrorResponse.of(errorCode);
        HttpStatus status = HttpStatus.valueOf(errorCode.getStatus());
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<MErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        System.out.println("MethodArgumentNotValidException 에러를 캐치");
        return null;
    }
}
