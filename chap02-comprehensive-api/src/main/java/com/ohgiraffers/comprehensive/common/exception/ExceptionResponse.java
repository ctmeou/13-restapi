package com.ohgiraffers.comprehensive.common.exception;

import com.ohgiraffers.comprehensive.common.exception.type.ExceptionCode;
import lombok.Getter;

@Getter
public class ExceptionResponse {

    private int code;
    private String message;

    public ExceptionResponse(final ExceptionCode exceptionCode) {

        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMessage();

    }

    public ExceptionResponse(int code, String message) {

        this.code = code;
        this.message =message;

    }

    public static ExceptionResponse of(int code, String message) {
        //of 메소드 정의하고 생성자로 전달

        return new ExceptionResponse(code, message);

    }

}
