package com.ohgiraffers.comprehensive.login.service;

import com.ohgiraffers.comprehensive.member.domain.Member;
import com.ohgiraffers.comprehensive.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//인증 매니저가 사용할 서비스에서도 구현을 무조건 해야 하는 loadUserByUsername 메소드
@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {

    private final MemberRepository memberRepository; //memberRepository에서 memberId를 찾아온다.

    @Override //반환 타입은 스프링 시큐리티에서 지정해놓은 UserDetails 타입
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException { //memberId가 넘어오면서 메소드가 호출된다.

        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 아이디가 존재하지 않습니다."));

        return User.builder() //builder 패턴의 메소드를 호출해서 user타입을 하나 만드는데 DB에서 조회한 username, password, roles를 가져와서 user 객체를 반환한다.
                .username(member.getMemberId())
                .password(member.getMemberPassword())
                .roles(member.getMemberRole().name())
                .build();

    }

}
