package com.ohgiraffers.comprehensive.common.exception.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionCode { //enum은 사용하고 싶은 상수 타입을 나열하는데, 현재 정의하는 코드를 나열한다.

    FAIL_TO_UPLOAD_FILE(1001, "파일 저장에 실패하였습니다."),

    FAIL_TO_DELETE_FILE(1002, "파일 삭제에 실패하였습니다."),

    NOT_FOUND_CATEGORY_CODE(2000, "카테고리 코드에 해당하는 카테고리가 존재하지 않습니다."),

    NOT_FOUND_PRODUCT_CODE(3000, "상품 코드에 해당하는 상품이 존재하지 않습니다."),

    FAIL_LOGIN(4000, "로그인 실패하였습니다."),

    UNAUTHORIZED(4001, "인증되지 않은 요청입니다."),

    NOT_FOUND_MEMBER_ID(4002, "아이디에 해당하는 유저가 없습니다."),

    ACCESS_DENIED(4003, "허가되지 않은 요청입니다.");

    //enum에서 필드 선언이 가능하고 생성자를 만든 후 코드와 메시지를 세팅한다.
    private final int code;
    private final String message;

}
