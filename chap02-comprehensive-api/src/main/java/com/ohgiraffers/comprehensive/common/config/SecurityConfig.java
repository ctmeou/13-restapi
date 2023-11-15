package com.ohgiraffers.comprehensive.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohgiraffers.comprehensive.login.filter.CustomUsernamePasswordAuthenticationFilter;
import com.ohgiraffers.comprehensive.login.handler.LoginFailureHandler;
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

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http  //여기에 설정한 인증 인가에 대해 build하면 Security에 대한 설정
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
                // 이 떄 OPTIONS 메소드로 서버에 사전 요청을 보내 권한을 확인한다.
                //antMatchers을 이용해서 HttpMethod.OPTIONS 메소드의 요청에 대해 허용
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll() //브라우저에 대한 사전 요청
                .antMatchers(HttpMethod.GET, "api/v1/products/**").permitAll() //상품 목록이나 상품 상세 정보를 볼 때 인증되지 않아도 볼 수 있게 get 방식으로 비로그인 상태로 볼 수 있게 설정
                .antMatchers("/member/signup").permitAll() //회원가입도 비로그인 상태로 할 수 있다.
                .antMatchers("/api/v1/product-management/**", "/api/v1/products/**").hasRole("ADMIN") //관리자가 수행할 일
                .anyRequest().authenticated() //여기에 작성한 것 외의 것은 로그인해야 이용할 수 있다.
                .and()
                // 로그인 필터 설정
                .addFilterBefore(customUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class) //커스텀에서 만든 로그인 시 동작 : addFilterBefore, 필터 체인이 있고 필터 앞에 UsernamePasswordAuthenticationFilter.class 끼워넣는다.
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
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); //Origins에서 오는 것이 프로토콜, ip와 port이고 그것을 허용한다. asList는 하나의 요청 이후 여러 요청을 할 수 있기에 작성했다.
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "PUT", "POST", "DELETE"));
        corsConfiguration.setAllowedHeaders(Arrays.asList(
                "Access-Control-Allow-Origin", "Access-Control-ALlow-Headers",
                "Content-Type", "Authorization", "X-Requested-With"
        ));
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

    /* 로그인 인증 필터 빈 등록 */
    @Bean
    public CustomUsernamePasswordAuthenticationFilter customUsernamePasswordAuthenticationFilter() {

        CustomUsernamePasswordAuthenticationFilter customUsernamePasswordAuthenticationFilter
                = new CustomUsernamePasswordAuthenticationFilter(objectMapper); //objectMapper 의존성 주입 후 넣어준다.

        /* 사용할 인증 매니저 설정 */ //위의 ProviderManager의 설정 작성
        customUsernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManager());
        /* 로그인 실패 핸들링 */ //LoginFailureHandler를 작성 후에 선언한다.
        customUsernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(loginFailureHandler());

        return customUsernamePasswordAuthenticationFilter;

    }

}
