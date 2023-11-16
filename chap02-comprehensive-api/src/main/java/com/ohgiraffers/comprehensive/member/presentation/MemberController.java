package com.ohgiraffers.comprehensive.member.presentation;

import com.ohgiraffers.comprehensive.member.dto.request.MemberSignupRequest;
import com.ohgiraffers.comprehensive.member.dto.response.ProfileResponse;
import com.ohgiraffers.comprehensive.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping({"/member", "api/v1/member"})
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /* 1. 회원가입 */
    //http 메소드 -> Post
    @PostMapping("/signup")                     //데이터 검증
    public ResponseEntity<Void> singup(@RequestBody @Valid MemberSignupRequest memberRequest) {

        memberService.signup(memberRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build(); //상품 등록이 아닌 회원가입에서는 URI 없이 상태코드만 전달한다.

    }

    /* 2. 프로필 조회 */
    @GetMapping //이 동작이 일어나기 전에 인증 필터에서 인증 처리가 먼저 일어난다.
    public ResponseEntity<ProfileResponse> profile(@AuthenticationPrincipal User user) {

        //조회할 조건이 ID(username)로 담겨있다.
        ProfileResponse profileResponse = memberService.getProfile(user.getUsername());

        return ResponseEntity.ok(profileResponse); //200번 상태 코드와 함께 조회된 내용 조회

    }

}
