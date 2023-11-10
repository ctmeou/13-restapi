package com.ohgiraffers.restapi.section04.hateoas;

import com.ohgiraffers.restapi.section02.responseentity.ResponseMessage;
import com.ohgiraffers.restapi.section02.responseentity.UserDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/hateoas")
public class HateoasTestController {

    private List<UserDTO> users;

    public HateoasTestController() {
        users = new ArrayList<>();
        users.add(new UserDTO(1, "user01", "pass01", "뽀또", new Date()));
        users.add(new UserDTO(2, "user02", "pass02", "쿠크다스", new Date()));
        users.add(new UserDTO(3, "user03", "pass03", "레이즈", new Date()));
    }

    @GetMapping("/users")
    public ResponseEntity<ResponseMessage> findAllUsers() { //ResponseEntity를 통해 반환하고 <> 안에는 응답에 대한 데이터를 정해놓고 사용할 수도 있다. 현재 별도 선언(ResponseMessage)

        /* 응답 헤더 설정 */
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        /* hateoas 설정 */
        //EntityModel의 userList를 만들고 관계있는 링크까지 포함해서 만드는 것이 목적
        List<EntityModel<UserDTO>> userWithRel =
                users.stream().map(user -> EntityModel.of(user, //가공하기 위해 map 메소드 이용, EntityModel.of에 첫 번째 인자로 실질적으로 넘길 데이터와, 두 번째인자에는 첫 번째 링크와 두 번째 링크를 넘긴다.
                        linkTo(methodOn(HateoasTestController.class).findUserByNo(user.getNo())).withSelfRel(),
                        linkTo(methodOn(HateoasTestController.class).findAllUsers()).withRel("users")
                        )).collect(Collectors.toList());

        /* 응답 데이터 설정 */
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("users", userWithRel);
        ResponseMessage responseMessage = new ResponseMessage(200, "조회 성공", responseMap);
        //원하는 상태값, 원하는 메시지, 원하는 응답 데이터 작성

        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);

    }

    //특정 user 정보 찾는 동작
    //{ userNo } : PathVariable 해서 넘겨야 하기 때문에 중괄호로 작성한다.
    @GetMapping("/users/{userNo}")
    public ResponseEntity<ResponseMessage> findUserByNo(@PathVariable int userNo) {

        /* 응답 헤더 설정 */
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        UserDTO foundUser = users.stream()
                .filter(user -> user.getNo() == userNo)
                .collect(Collectors.toList()).get(0); //userNo가 일치하는 user를 찾아오는 로직

        /* 응답 데이터 설정 */
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("user", foundUser);
        ResponseMessage responseMessage = new ResponseMessage(200, "조회 성공", responseMap);

        return ResponseEntity
                .ok() //상태 값 ok 설정
                .headers(headers) //ResponseEntity 객체를 대상으로 header 설정하고 싶을 때 호출하는 메소드
                .body(responseMessage); //ResponseEntity 객체를 대상으로 body 설정하고 싶을 때 호출하는 메소드

    }

}
