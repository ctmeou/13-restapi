package com.ohgiraffers.comprehensive.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;

public class SecurityConfig {

    /* CORS(Cross Origin Resource Sharing) : 교차 출처 자원 공유
    * 보안상 웹 브라우저는 다른 도메인에서 서버의 자원을 요청하는 경우 막아 놓았다.
    * 기본적으로 서버에서 클라이언트를 대상으로 리소스 허용 여부를 결정한다. */
    @Bean //CorsConfigurationSource type의 Bean 등록
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 로컬 React에서 오는 요청은 허용한다.
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); //Origins에서 오는 것이 프로토콜, ip와 port이고 그것을 허용한다. asList는 하나의 요청 이후 여러 요청을 할 수 있기에 작성했다.
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "PUT", "POST", "DELETE"));

        return null;

    }

}
