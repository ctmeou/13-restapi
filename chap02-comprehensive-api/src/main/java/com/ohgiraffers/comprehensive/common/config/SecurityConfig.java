package com.ohgiraffers.comprehensive.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity //Security 설정 활성화
public class SecurityConfig {

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
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(HttpMethod.GET, "api/v1/products/**").permitAll()
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

}
