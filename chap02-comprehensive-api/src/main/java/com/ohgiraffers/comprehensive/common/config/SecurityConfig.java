package com.ohgiraffers.comprehensive.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohgiraffers.comprehensive.jwt.filter.JwtAuthenticationFilter;
import com.ohgiraffers.comprehensive.jwt.handler.JwtAccessDeniedHandler;
import com.ohgiraffers.comprehensive.jwt.handler.JwtAuthenticationEntryPoint;
import com.ohgiraffers.comprehensive.jwt.service.JwtService;
import com.ohgiraffers.comprehensive.login.filter.CustomUsernamePasswordAuthenticationFilter;
import com.ohgiraffers.comprehensive.login.handler.LoginFailureHandler;
import com.ohgiraffers.comprehensive.login.handler.LoginSuccessHandler;
import com.ohgiraffers.comprehensive.login.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity //Security 설정 활성화
@RequiredArgsConstructor
public class SecurityConfig {

    private final ObjectMapper objectMapper;
    private final LoginService loginService;
    private final JwtService jwtService;

    /* 테스트
      1. Token 값이 없거나 잘못 작성된 경우
        GET http://localhost:8001/member/hello로 token 없이 → 인증되지 않은 요청
      2. AccessToken 유효한 경우
        GET http://localhost:8001/member/hello로 token 가지고 → 인증되어 404
      3. AccessToken 유효하지 않고 RefreshToken 유효한 경우
        accessToken 시간 설정 짧게
        현재 refreshToken 확인 후 업데이트 되는지
        GET http://localhost:8001/member/hello로 access token 가지고 → 인증되지 않은 요청
        GET http://localhost:8001/member/hello로 refresh token 가지고 → 헤더 응답으로 새로운 access token, refresh token 발급
        GET http://localhost:8001/member/hello로 재발급 받은 access token 가지고 요청하면 된다.(시간이 짧게 설정되어서 다시 만료로 뜨지만)
      4. AccessToken 유효하지만 권한이 없는 경우
        GET http://localhost:8001/api/v1/products-management?page=1로 일반 유저 로그인 후 발급 받은 accessToken 가지고 → 허가되지 않은 요청
        GET http://localhost:8001/api/v1/products-management?page=1로 관리자 유저 로그인 후 발급 받은  accessToken 가지고 → 조회 완료
    */

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http //여기에 설정한 인증 인가에 대해 build하면 Security에 대한 설정
                // CSRF 설정 비활성화
                .csrf()
                .disable()
                // API 서버는 session을 사용하지 않으므로  STATELESS 설정
                //token 방식으로 처리할 것이기 때문에 세션에 대한 부분은 상태값 없음으로 설정한다.
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 요청에 대한 권한 체크
                .authorizeRequests()
                // 클라이언트가 외부 도메인을 요청하는 경우 웹 브라우저에서 자체적으로 사전 요청(preFlight)이 일어난다.
                // 이 때 OPTIONS 메소드로 서버에 사전 요청을 보내 권한을 확인한다.
                //antMatchers을 이용해서 HttpMethod.OPTIONS 메소드의 요청에 대해 허용
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll() //브라우저에 대한 사전 요청
                .antMatchers(HttpMethod.GET, "/api/v1/products/**").permitAll() //상품 목록이나 상품 상세 정보를 볼 때 인증되지 않아도 볼 수 있게 get 방식으로 비로그인 상태로 볼 수 있게 설정
                .antMatchers("/member/signup").permitAll() //회원가입도 비로그인 상태로 할 수 있다.
                .antMatchers("/api/v1/products-management/**", "/api/v1/products/**").hasRole("ADMIN") //관리자가 수행할 일
                .anyRequest().authenticated() //여기에 작성한 것 외의 것은 로그인해야 이용할 수 있다.
                .and()
                // 로그인 필터 설정
                .addFilterBefore(customUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class) //커스텀에서 만든 로그인 시 동작 : addFilterBefore, 필터 체인이 있고 필터 앞에 UsernamePasswordAuthenticationFilter.class 끼워넣는다.
                // JWT Token 인증 필터 설정(로그인 필터 앞에 설정)
                .addFilterBefore(jwtAuthenticationFilter(), CustomUsernamePasswordAuthenticationFilter.class)
                // exception handing 설정
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint())
                .accessDeniedHandler(jwtAccessDeniedHandler())
                .and()
                // 교차 출처 자원 공유 설정
                .cors()
                .and()
                .build();

    }

    /* CORS(Cross Origin Resource Sharing) : 교차 출처 자원 공유
    * 보안상 웹 브라우저는 다른 도메인에서 서버의 자원을 요청하는 경우 막아 놓았다.
    * 기본적으로 서버에서 클라이언트를 대상으로 리소스 허용 여부를 결정한다. */
    @Bean //CorsConfigurationSource type의 Bean 등록
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 로컬 React에서 오는 요청은 허용한다.
        //요청에 대한 설정(103~107)
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); //Origins에서 오는 것이 프로토콜, ip와 port이고 그것을 허용한다. asList는 하나의 요청 이후 여러 요청을 할 수 있기에 작성했다.
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "PUT", "POST", "DELETE"));
        corsConfiguration.setAllowedHeaders(Arrays.asList(
                "Access-Control-Allow-Origin", "Access-Control-ALlow-Headers",
                "Content-Type", "Authorization", "X-Requested-With", "Access-Token", "Refresh-Token"));
        //응답에 대한 설정(110)
        corsConfiguration.setExposedHeaders(Arrays.asList("Access-Token", "Refresh-Token"));
        // 모든 요청 url 패턴에 대해 위의 설정을 적용한다.
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /* 인증 매니저 빈 등록 => 로그인 시 사용할 password encode 설정, 로그인 시 유저 조회하는 메소드를 가진 Service 클래스 설정 */
    @Bean
    public AuthenticationManager authenticationManager() {
        //인증 매니저가 어떻게 동작할 것인지 설정
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(loginService); //의존성 주입 선언 후 loginService
        return new ProviderManager(provider);
    }

    /* 로그인 실패 핸들러 빈 등록 */
    @Bean
    public LoginFailureHandler loginFailureHandler() {
        return new LoginFailureHandler(objectMapper);
    }

    /* 로그인 성공 핸들러 빈 등록 */
    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler(jwtService); //생성 시 service가 넘어가야 생성이 된다.
    }

    /* 로그인 인증 필터 빈 등록 */
    @Bean
    public CustomUsernamePasswordAuthenticationFilter customUsernamePasswordAuthenticationFilter() {

        CustomUsernamePasswordAuthenticationFilter customUsernamePasswordAuthenticationFilter
                = new CustomUsernamePasswordAuthenticationFilter(objectMapper); //objectMapper 의존성 주입 후 넣어준다.

        /* 사용할 인증 매니저 설정 */ //위의 ProviderManager의 설정 작성
        customUsernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManager());
        /* 로그인 실패 핸들링 */ //LoginFailureHandler를 작성 후에 선언한다.
        customUsernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(loginFailureHandler());
        /* 로그인 성공 핸들링 */
        customUsernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(loginSuccessHandler());

        return customUsernamePasswordAuthenticationFilter;

    }
    
    /* JWT 인증 필터 */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtService);
    }
    
    /* 인증 실패 핸들러 */
    @Bean
    public JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint() {
        return new JwtAuthenticationEntryPoint(objectMapper);
    }
    
    /* 인가 실패 핸들러 */
    @Bean
    public JwtAccessDeniedHandler jwtAccessDeniedHandler() {
        return new JwtAccessDeniedHandler(objectMapper);
    }

}
