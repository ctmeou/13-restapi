package com.ohgiraffers.comprehensive.login.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static java.util.jar.Attributes.Name.CONTENT_TYPE;

/* 스프링 시큐리티의 기존 UsernamePasswordAuthenticationFilter를 대체할 Custom Filter 작성 */
//로그인에 대한 필터를 custom한다.
//AuthenticationProcessingFilter을 상속 받아야 한다.
public class CustomUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    //생성자도 만들어준다.
    //생성자 목적 : 이 과정에서 요청에 대한 어떠한 처리를 할 것인지
    private static final String HTTP_METHOD = "POST";
    private static final String LOGIN_REQUEST_URL = "member/login";

    private static final String CONTENT_TYPE = "application/json";

    private static final String USERNAME = "memberId";
    private static final String PASSWORD = "memberPassword";

    private final ObjectMapper objectMapper;

    public CustomUsernamePasswordAuthenticationFilter(ObjectMapper objectMapper) { //post 방식으로 member login 요청이 왔을 경우 필터를 동작시키겠다.
        /* POST /member/login 요청이 올 때 해당 필터가 동작하도록 설정 */
        super(new AntPathRequestMatcher(LOGIN_REQUEST_URL, HTTP_METHOD)); //super로 전달한다는 것은 AbstractAuthenticationProcessingFilter 클래스에 전달한다. (필터가 매칭했으면 하는 요청)
        this.objectMapper = objectMapper;
    }

    //상속 받을 시 반드시 메소드 구현 후
    //인증 로직 작성
    //필터가 동작되면 아래의 메소드가 동작된다.
    /* POST "/member/login" 요청 발생 시 메소드 호출되며 인증 처리 작성 */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        //요청에 대해 받았을 때 필터이기 때문에 확인해야 할 것이 있다.(필터는 직접적으로 구현해야 하고 요청에 대해 올바른지 확인한다.)
        // Request Content Type "application/json" 확인
        if (request.getContentType() == null || !request.getContentType().equals(CONTENT_TYPE)) {
            throw new AuthenticationServiceException("Content-Type not supported");
        }

        // Request Body 읽어오기
        String body = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);

        // JSON 문자열을 Java Map 타입으로 변환
        Map<String, String> bodyMap = objectMapper.readValue(body, Map.class);

        // key 값을 전달해서 Map에서 id와 pwd 꺼내기
        String memberId = bodyMap.get(USERNAME);
        String memberPassword = bodyMap.get(PASSWORD);

        // 인증 토큰에 세팅
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId, memberPassword);
        //아이디와 비밀번호를 UsernamePasswordAuthenticationToken에 세팅을 먼저 한 후 인증 매니저에게 전달

        // 인증 매니저에게 인증 토큰 전달
        return this.getAuthenticationManager().authenticate(authenticationToken);

    }

}
