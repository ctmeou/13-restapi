package com.ohgiraffers.comprehensive.member.service;

import com.ohgiraffers.comprehensive.common.exception.BadRequestException;
import com.ohgiraffers.comprehensive.member.domain.Member;
import com.ohgiraffers.comprehensive.member.domain.repository.MemberRepository;
import com.ohgiraffers.comprehensive.member.dto.request.MemberSignupRequest;
import com.ohgiraffers.comprehensive.member.dto.response.ProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ohgiraffers.comprehensive.common.exception.type.ExceptionCode.NOT_FOUND_MEMBER_ID;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /* 1. 회원 가입 */
    public void signup(final MemberSignupRequest memberRequest) {

        final Member newMember = Member.of(
                memberRequest.getMemberId(),
                passwordEncoder.encode(memberRequest.getMemberPassword()),
                memberRequest.getMemberName(),
                memberRequest.getMemberEmail()
        );

        memberRepository.save(newMember); //엔티티를 통해 save한다.

    }

    /* 프로필 조회 */
    @Transactional(readOnly = true)
    public ProfileResponse getProfile(String memberId) {

        final Member member = memberRepository.findByMemberId(memberId) //memberId 기준으로 조회하고
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_MEMBER_ID)); //없으면 NOT_FOUND_MEMBER_ID exception 발생

        //엔티티 member를 그대로 반환하는 것이 아닌 필요한 형태로 반환하는 것이 목적 -> ProfileResponse에 from 생성
        return ProfileResponse.from(member);

    }

}
