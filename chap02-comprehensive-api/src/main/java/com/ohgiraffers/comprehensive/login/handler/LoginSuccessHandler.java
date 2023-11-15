package com.ohgiraffers.comprehensive.login.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

/* 로그인 성공 핸들러 */
//로그인 성공 시 AuthenticationManager 체킹 후 SecuirtyContext에 인증 객체 저장한다.
@Slf4j
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override                                                                     //성공 시 authentication를 가질 수 있고 저장할 수 있는 인증 객체이다.
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        /* 로그인 성공 후 저장된 인증 객체에서 정보를 꺼낸다. */
        Map<String, String> memberInfo = getMemberInfo(authentication); //authentication이 전달됐고 member 정보를 꺼내고 map으로 담는다.
        log.info("로그인 성공 후 인증 객체에서 꺼낸 정보 : {}", memberInfo);

    }

    private Map<String, String> getMemberInfo(Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal(); //LoginService에 있는 값을 꺼내고
        String memberRole = userDetails.getAuthorities() //Authorities 부분을 가공한다.
                .stream().map(auth -> auth.getAuthority().toString())
                .collect(Collectors.joining()); //배열에 있는 데이터를 joining

        //user인지 admin인지에 대한 정보를 뽑는다.
        return Map.of(
                "memberId", userDetails.getUsername(),
                "memberRole", memberRole
        );

    }

}
