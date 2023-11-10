package com.ohgiraffers.restapi.section03;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//Error에 대한 응답 패키지 생성
@AllArgsConstructor
@Getter @Setter @ToString
public class ErrorResponse {

    private String code;
    private String description;
    private String detail;

}
