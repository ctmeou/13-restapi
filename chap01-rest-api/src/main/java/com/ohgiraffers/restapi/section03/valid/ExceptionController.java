package com.ohgiraffers.restapi.section03;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//공통적으로 Excption을 잡아줄 클래스
@ControllerAdvice //ExceptionHandler을 전역적으로 사용
public class ExceptionController {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserException(UserNotFoundException e) {

        String code = "ERROR_CODE_00000";
        String description ="회원 정보 조회 실패";
        String detail = e.getMessage();

        return new ResponseEntity<>(new ErrorResponse(code, description, detail), HttpStatus.NOT_FOUND); //상태 코드 NOT FOUND 404

    }

}
