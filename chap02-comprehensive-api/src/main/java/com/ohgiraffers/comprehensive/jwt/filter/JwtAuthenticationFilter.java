package com.ohgiraffers.comprehensive.jwt.filter;

import com.ohgiraffers.comprehensive.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/* Request에서 Token을 꺼내서 인증 확인하는 필터
* (로그인 외에 인증이 필요한 요청들을 처리) */
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter { //한 번만 통과하는 필터

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        /* 로그인 요청의 경우 다음 필터(로그인 필터)로 진행 */
        if (request.getRequestURI().equals("/member/login")) { //요청에 주소를 얻고
            filterChain.doFilter(request, response); //다음 필터로 이동하고 그 이상의 진행은 막는다.
            return;
        } //if문에 걸리지 않았다는 건 로그인 요청이 아니다.

        /* 1. 사용자 헤더에서 Refresh Token 추출 */
        String refreshToken = jwtService.getRefreshToken(request) //요청 주소에서 RefreshToken를 꺼내온다.
                .filter(jwtService::isValidToken) //토큰이 있기만 하면 되는 것이 아니라 토큰이 맞는지, 유효시간이 초과하지 않은 유효한 토큰인지 확인해야 한다.
                .orElse(null); //true인 경우 refreshToken에 들어가지만 아닌 경우 null이 반환된다.

        /* 2-1. Refresh Token이 있다면?
        * - AccessToken 만료 상황
        * - DB에서 Refresh Token 일치 여부 확인 후 일치하면 AccessToken 재발급 */ //DB와 일치하면 재발급, 불일치하면 실패
        if (refreshToken != null) {
            jwtService.checkRefreshTokenAndReIssueAccessToken(response, refreshToken);
            return;
        }


        /* 2-2. Refresh Token이 없다면?
        * - AccessToken 유효성 확인 */ //유효하면 진행, 유효하지 않으면 진행 X

    }

}
