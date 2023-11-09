package com.ohgiraffers.restapi.section01.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter @ToString
public class Message {

    private int httpStatusCode; //응답을 가지는 코드(200, 404, 500 등)

    private String message;


}
