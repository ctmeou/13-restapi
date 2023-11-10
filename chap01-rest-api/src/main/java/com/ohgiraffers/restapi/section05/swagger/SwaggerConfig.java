package com.ohgiraffers.restapi.section05.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.HashSet;
import java.util.Set;

//Swagger를 사용하기 위한 기본 설정
@Configuration
@EnableWebMvc
public class SwaggerConfig {

    //기본적인 app 정보
    private ApiInfo swaggerInfo() { //ApiInfo를 만들기 위한 메소드

        return new ApiInfoBuilder()
                .title("Ohgiraffers API")
                .description("spring boot swagger 연동 테스트")
                .build();

    }

    //어떤 컨텐츠 타입(json)을 사용하는지에 대한 Stirng 형태의 Set으로 담는다.
    private Set<String> getConsumeContentTypes() {

        //이러한 컨텐츠 타입을 받아들일 수 있다.
        Set<String> consumes = new HashSet<>();
        consumes.add("application/json;charset=UTF-8"); //받아들이는 형식에 따라 작성이 가능하다.
        consumes.add("application/x-www-form-urlencoded");

        return consumes;

    }


    private Set<String> getProduceContentTypes() {

        Set<String> produces = new HashSet<>();
        produces.add("application/json;charset=UTF-8");

        return produces;

    }

    @Bean
    public Docket swaggerApi() {

        //문서 타입, 입력 정보, 어떤 API를 선택해서 만들지, 기본적인 응답 메시지 설정
        return  new Docket(DocumentationType.SWAGGER_2)
                .consumes(getConsumeContentTypes()) //위에서 선언한 info, content에 반환하는 메소드를 사용한다.
                .produces(getProduceContentTypes())
                .apiInfo(swaggerInfo())
                .select()
                //어떤 것을 넣을 건지 범위 설정이 가능하다.
                // 모든 경로를 API 문서화
                // .apis(RequestHandlerSelectors.any())
                // 지정된 패키지만 API 문서화
                .apis(RequestHandlerSelectors.basePackage("com.ohgiraffers.restapi.section05"))
                // 모든 URL 패턴에 대한 API 문서화
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false);

    }

}
