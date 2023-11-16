package com.ohgiraffers.comprehensive.jwt.service;

import com.ohgiraffers.comprehensive.common.exception.BadRequestException;
import com.ohgiraffers.comprehensive.member.domain.Member;
import com.ohgiraffers.comprehensive.member.domain.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import static com.ohgiraffers.comprehensive.common.exception.type.ExceptionCode.NOT_FOUND_MEMBER_ID;

@Service
@Slf4j
public class JwtService {

    @Value("${jwt.access.expiration}")
    private Long accessTokenExpirationPeriod;
    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenExpirationPeriod;

    private final Key key;
    private final MemberRepository memberRepository;

    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String BEARER = "Bearer ";

    public JwtService(@Value("${jwt.secret}") String secretKey, MemberRepository memberRepository) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes); //전달하면서 인증키를 생성한다.
        this.memberRepository = memberRepository;
    }

    public String createAccessToken(Map<String, String> memberInfo) {

        Claims claims = Jwts.claims().setSubject(ACCESS_TOKEN_SUBJECT);
        claims.putAll(memberInfo);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpirationPeriod)) //만료 시간 설정
                .signWith(key, SignatureAlgorithm.HS512) //시큐리티 포함을 해야 하고(서명 작성)
                .compact();

    }

    public String createRefreshToken() {

        return Jwts.builder()
                .setSubject(REFRESH_TOKEN_SUBJECT)
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpirationPeriod))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

    }

    @Transactional //DB에 실제 업데이트가 되기 위해 작성
    public void updateRefreshToken(String memberId, String refreshToken) {

        //memberRepository가 필요하다. 사용하기 위해 의존성 주입을 한다.
        //RefreshToken 업데이트 시 사용한다.
        memberRepository.findByMemberId(memberId)
                .ifPresentOrElse( //optional type에 대한 handling
                        member -> member.updateRefreshToken(refreshToken), //있을 경우 업데이트 로직 실행
                        () -> new BadRequestException(NOT_FOUND_MEMBER_ID) //없을 경우 exception 발생
                );

    }

    public Optional<String> getRefreshToken(HttpServletRequest request) { //RefreshToken이 header에 있을 수도 있고 없을 수도 있기 때문에 optional 설정을 한다.

        return Optional.ofNullable(request.getHeader("Refresh-Token")) //null일 수도 있고 아닐 수도 있는 어떤 값을 처리한다.
                .filter(refreshToken -> refreshToken.startsWith(BEARER)) //있으면 BEARER 로 시작하는지 확인하고
                .map(refreshToken -> refreshToken.replace(BEARER, "")); //시작하면 앞의 문자열은 제거하고 나머지 문자열만 반환하고 없으면 null이라는 값을 optional에 반환한다.

    }

    public Optional<String> getAccessToken(HttpServletRequest request) {

        return Optional.ofNullable(request.getHeader("Access-Token")) //Access-Token으로 넘어오는지 확인하고
                .filter(accessToken -> accessToken.startsWith(BEARER)) //올바른 형태였으면
                .map(accessToken -> accessToken.replace(BEARER, "")); //앞에 제거 후 반환
    }

    public boolean isValidToken(String token) {

        try { //유효성 판단
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.error("유효하지 않은 토큰입니다. {}", e.getMessage());
            return false;
        }

    }

    public void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken) {

        //DB에서 찾아서 확인한다.
        memberRepository.findByRefreshToken(refreshToken)
                .ifPresent(member -> {
                    String reIssuedRefreshToken = reIssuedRefreshToken(member);

                    String accessToken = createAccessToken(
                            Map.of("memberId", member.getMemberId(), "memberRole", member.getMemberRole().name())
                    );
                    response.setHeader("Access-Token", accessToken);
                    response.setHeader("Refresh-Token", reIssuedRefreshToken);
                });

    }

    private String reIssuedRefreshToken(Member member) {

        String reIssuedRefreshToken = createRefreshToken();
        member.updateRefreshToken(reIssuedRefreshToken);
        memberRepository.saveAndFlush(member);

        return reIssuedRefreshToken;

    }

    public void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        getAccessToken(request)
                .filter(this::isValidToken)
                .ifPresent(accessToken -> getMemberId(accessToken)
                        .ifPresent(memberId -> memberRepository.findByMemberId(memberId) //memberId가 있으면 DB에서 조회를 해온다. -> 반환 값이 optionalMember
                                .ifPresent(this::saveAuthentication))); //인증객체로 저장한다.

        filterChain.doFilter(request, response);

    }

    private Optional<String> getMemberId(String accessToken) {

        //memberId를 디코딩해서 가져온다.
       try{
           return Optional.ofNullable(
                   Jwts.parserBuilder() //parsing 처리할 객체 선언
                           .setSigningKey(key) //서명 키 전달
                           .build()
                           .parseClaimsJws(accessToken) //클레임 : 몸체, 정보가 담겨있고
                           .getBody() //accessToken으로 담아와쏙
                           .get("memberId").toString()
           );
       } catch (Exception e) {
           log.error("Access Token이 유효하지 않습니다.");
           return Optional.empty(); //parsing 처리 안에서 문제가 생기면 여기로 넘어온다.
       }

    }

    public void saveAuthentication(Member member) {

        UserDetails userDetails = User.builder()
                .username(member.getMemberId())
                .password(member.getMemberPassword())
                .roles(member.getMemberRole().name()) //roles은 문자열로 된 이름을 넣는다.
                .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        //(인증주체, 비밀번호(현재 상황에서 setting할 필요 x, 로그인 여부 확인하지 않음), user/admin 세팅이 됨)

        SecurityContextHolder.getContext().setAuthentication(authentication); //user/admin 판단하려면 객체가 메모리에 저장이되어야 한다.

    }

}
