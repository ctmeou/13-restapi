package com.ohgiraffers.comprehensive.common.exception.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionCode { //enum은 사용하고 싶은 상수 타입을 나열하는데, 현재 정의하는 코드를 나열한다.

    NOT_FOUND_PRODUCT_CODE(3000, "상품 코드에 해당하는 상품이 존재하지 않습니다.");

    //enum에서 필드 선언이 가능하고 생성자를 만든 후 코드와 메시지를 세팅한다.
    private final int code;
    private final String message;

}
