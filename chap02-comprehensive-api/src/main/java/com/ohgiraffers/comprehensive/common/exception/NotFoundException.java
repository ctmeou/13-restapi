package com.ohgiraffers.comprehensive.common.exception;

import com.ohgiraffers.comprehensive.common.exception.type.ExceptionCode;

public class NotFoundException extends CustomException {

    public NotFoundException(final ExceptionCode exceptionCode) {
        super(exceptionCode);
    }

}
