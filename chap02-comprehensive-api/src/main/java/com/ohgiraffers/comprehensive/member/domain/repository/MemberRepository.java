package com.ohgiraffers.comprehensive.member.domain.repository;

import com.ohgiraffers.comprehensive.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByMemberId(String memberId); //Member가 아닌  Optional<Member>를 작성한다. -> Id를 잘못 입력했을 경우에는 null이기 때문에 optional하게 설정하기 위해서

    Optional<Member> findByRefreshToken(String refreshToken); //refreshToken을 가진 Member를 찾고 없으면 null로 받환한다.
}
