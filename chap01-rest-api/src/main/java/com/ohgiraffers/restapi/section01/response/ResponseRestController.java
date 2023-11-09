package com.ohgiraffers.restapi.section01.response;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //1. bean Scan을 한다. 2. @RequestMapping과 관련된 어노테이션을 쓸 수 있다. 3. @ResponseBody를 붙인 것과 같다(반환 값이 곧 응답 값이다.)
@RequestMapping("/response")
public class ResponseRestController {

    /* 1. 문자열 응답 */
    @GetMapping("/hello")
    public String helloWorld() { //일반 Controller이었으면

        return "Hello World";

    }

    /* 2. 기본 자료형 응답 */
    @GetMapping("/random")
    public int getRandomNumebr() {

        return (int)(Math.random() * 10) + 1;

    }

    //=> @RestController이기 때문에 @ResponseBody를 굳이 작성하지 않아도 반환하는 값이 응답 값이되고있다.

}
