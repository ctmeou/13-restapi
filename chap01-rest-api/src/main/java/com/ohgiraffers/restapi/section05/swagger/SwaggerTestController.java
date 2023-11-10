package com.ohgiraffers.restapi.section05.swagger;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

@Api(tags = {"스프링부트 스웨거 연동 테스트용 컨트롤러"})
@RestController
@RequestMapping("/swagger")
public class SwaggerTestController {

    private List<UserDTO> users;

    public SwaggerTestController() {
        users = new ArrayList<>();
        users.add(new UserDTO(1, "user01", "pass01", "뽀또", new Date()));
        users.add(new UserDTO(2, "user02", "pass02", "쿠크다스", new Date()));
        users.add(new UserDTO(3, "user03", "pass03", "레이즈", new Date()));
    }

    @ApiOperation(value = "모든 회원 목록 조회") //ApiOperation : 문서화됐을 때 설명해주는 어노테이션
    @GetMapping("/users")
    public ResponseEntity<ResponseMessage> findAllUsers() { //ResponseEntity를 통해 반환하고 <> 안에는 응답에 대한 데이터를 정해놓고 사용할 수도 있다. 현재 별도 선언(ResponseMessage)

        /* 응답 헤더 설정 */
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        /* 응답 데이터 설정 */
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("users", users);
        ResponseMessage responseMessage = new ResponseMessage(200, "조회 성공", responseMap);
                                                              //원하는 상태값, 원하는 메시지, 원하는 응답 데이터 작성

        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);

    }

    //특정 user 정보 찾는 동작
    //{ userNo } : PathVariable 해서 넘겨야 하기 때문에 중괄호로 작성한다.
    @ApiOperation(value = "회원 번호로 회원 조회")
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

    //PostMapping은 user에 새로운 리소스 추가
    @ApiOperation(value = "신규 회원 추가")
    @PostMapping("/users")            //어떤 정보를 등록하는지에 대한 파라미터(기본적으로 json 형태이기 때문에 @RequestBody 작성)
    public ResponseEntity<Void> registUser(@RequestBody UserDTO newUser) {

        //DB 로직이 없어서 작성
        int lastUserNo = users.get(users.size() - 1).getNo();
        newUser.setNo(lastUserNo + 1);
        newUser.setEnrollDate(new Date());
        users.add(newUser);

        //새로운 리소스 생성 시에 대한 규칙
        return ResponseEntity
                .created(URI.create("/entity/users" + users.get(users.size() -  1).getNo()))
                .build();

    }

    //존재하는 리소스를 교체(수정)
    @ApiOperation(value = "모든 회원 정보 수정")
    @PutMapping("/users/{userNo}")
    public ResponseEntity<Void> modifyUser(@PathVariable int userNo, @RequestBody UserDTO modifyInfo) {

        //수정할 user 먼저 찾기
        UserDTO foundUser = users.stream()
                .filter(user -> user.getNo() == userNo)
                .collect(Collectors.toList()).get(0); //userNo가 일치하는 user를 찾아오는 로직

        //찾아온 user에 정보를 바꾼다.
        foundUser.setId(modifyInfo.getId());
        foundUser.setPwd(modifyInfo.getPwd());
        foundUser.setName(modifyInfo.getName());

        //여기서는, userNo로 참조가 가능해서 userNo로 작성했다.
        return ResponseEntity
                .created(URI.create("/entity/users" + userNo))
                .build();

    }

    @ApiOperation(value = "모든 회원 정보 삭제")
    @DeleteMapping("/users/{userNo}")
    public ResponseEntity<Void> removeUser(@PathVariable int userNo) {

        //삭제할 user 찾기
        UserDTO foundUser = users.stream()
                .filter(user -> user.getNo() == userNo)
                .collect(Collectors.toList()).get(0); //userNo가 일치하는 user를 찾아오는 로직

        users.remove(foundUser);

        return ResponseEntity
                .noContent()
                .build();

    }

}
