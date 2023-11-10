package com.ohgiraffers.restapi.section03.valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/valid")
public class ValidTestController {

    @GetMapping("/users/{userNo}")
    public ResponseEntity<Void> findUserByNo() throws UserNotFoundException {

        boolean check = true;
        if (check) {
            throw new UserNotFoundException("회원 정보를 찾을 수 없습니다.");
        }

        return ResponseEntity.ok().build();

    }

    //UserDTO 테스트 해보기 위한 메소드 작성
    //데이터 검증을 하고 맞지 않으면
    @PostMapping("/users")
    public ResponseEntity<Void> registUser(@Validated @RequestBody UserDTO user) { //유효성 검사를 하고 싶은 곳 앞에 @Validated 작성

        return ResponseEntity
                .created(URI.create("/valid/users/1"))
                .build();

    }

 }
