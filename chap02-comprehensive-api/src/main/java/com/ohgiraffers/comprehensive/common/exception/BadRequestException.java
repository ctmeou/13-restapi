package com.ohgiraffers.comprehensive.common.exception;

import com.ohgiraffers.comprehensive.common.exception.type.ExceptionCode;
import lombok.Getter;

//Exception이 아닌 RuntimeException을 상속 받으면 catch 안 해줘도 된다.
@Getter
public class BadRequestException extends RuntimeException { //잘못된 요청이 왔을 때

    private final int code;
    private final String message;

    public BadRequestException(final ExceptionCode exceptionCode) {

        //넘어오는 exceptionCode에 따라서 코드와 메시지가 정의된다.
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMessage();

    }

}
