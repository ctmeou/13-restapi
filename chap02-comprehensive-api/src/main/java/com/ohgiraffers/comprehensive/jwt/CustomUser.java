package com.ohgiraffers.comprehensive.jwt;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
public class CustomUser extends User {

    //프로그램 관전에 필요한 것을 정의
    private final Long memberCode;

    public CustomUser(Long memberCode, UserDetails userDetails) {
        super(userDetails.getUsername(),userDetails.getPassword(), userDetails.getAuthorities()); //User의 상위 타입으로 전달해야 하고
        this.memberCode = memberCode; //추가적으로 memberCode 세팅
    }

    public static CustomUser of(Long memberCode, UserDetails userDetails) {
        return new CustomUser(
                memberCode,
                userDetails
        );
    }

}
