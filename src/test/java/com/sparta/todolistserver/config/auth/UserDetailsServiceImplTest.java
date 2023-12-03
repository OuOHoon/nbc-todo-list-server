package com.sparta.todolistserver.config.auth;

import com.sparta.todolistserver.domain.Member;
import com.sparta.todolistserver.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    public void Given_Username_When_LoadUserByUsername_Then_ReturnUserDetailsImpl() throws Exception {
        //given
        Member member = Member.builder().username("user").build();
        when(memberRepository.findMemberByUsername(anyString()))
                .thenReturn(Optional.of(member));

        //when
        UserDetails userDetails = userDetailsService.loadUserByUsername("user");

        //then
        Assertions.assertThat(userDetails.getUsername()).isEqualTo(member.getUsername());
    }
}