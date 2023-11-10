package com.ohgiraffers.restapi.section03;

//사용자 정의 Exception 클래스 생성
public class UserNotFoundException extends Exception {

    public UserNotFoundException(String msg) { super(msg); }


}
