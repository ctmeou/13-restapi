package com.ohgiraffers.restapi.section05.swagger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@AllArgsConstructor
@Getter @Setter @ToString
public class ResponseMessage {

    private int httpStatus;
    private String message;
    private Map<String, Object> results; //응답할 데이터를 담는다(여러 개일 수도 있다.)
}
