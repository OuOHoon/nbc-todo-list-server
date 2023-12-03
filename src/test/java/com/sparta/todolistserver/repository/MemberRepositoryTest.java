package com.sparta.todolistserver.repository;

import com.sparta.todolistserver.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;


class MemberRepositoryTest extends BaseRepositoryTest{

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void findMemberByUsernameTest() throws Exception {
        //given
        String username = "user";
        Member member = Member.builder().username("user").password("password").build();
        memberRepository.save(member);

        //when
        Optional<Member> findMember = memberRepository.findMemberByUsername(username);

        //then
        Assertions.assertThat(findMember.get()).isEqualTo(member);
    }
}