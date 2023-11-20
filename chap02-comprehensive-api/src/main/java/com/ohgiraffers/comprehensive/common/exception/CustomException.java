package com.ohgiraffers.comprehensive.common.exception;

import com.ohgiraffers.comprehensive.common.exception.type.ExceptionCode;
import lombok.Getter;

//BadRequest, NotFound, ServerInternal이 extends 받도록 처리
@Getter
public class CustomException extends RuntimeException {

    //모든 exception이 가지고 있는 형태
    private final int code;
    private final String message;

    public CustomException(final ExceptionCode exceptionCode) {
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMessage();
    }

}
