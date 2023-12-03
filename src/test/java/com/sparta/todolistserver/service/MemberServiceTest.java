package com.sparta.todolistserver.service;

import com.sparta.todolistserver.domain.Card;
import com.sparta.todolistserver.domain.Member;
import com.sparta.todolistserver.repository.CardRepository;
import com.sparta.todolistserver.repository.MemberRepository;
import com.sparta.todolistserver.request.member.MemberCreateRequest;
import com.sparta.todolistserver.response.card.CardResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private CardRepository cardRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private MemberService memberService;

    @Test
    public void Given_MemberCreateRequest_When_Create_Then_SaveEntity() {
        //given
        MemberCreateRequest request =
                new MemberCreateRequest("username", "password");
        String encodedPassword = "encodedpassword";

        //when
        when(passwordEncoder.encode(anyString())).thenReturn(encodedPassword);
        memberService.create(request);
        ArgumentCaptor<Member> memberCaptor = ArgumentCaptor.forClass(Member.class);
        verify(memberRepository).save(memberCaptor.capture());
        Member savedMember = memberCaptor.getValue();

        //then
        assertThat(savedMember.getUsername()).isEqualTo(request.getUsername());
        assertThat(savedMember.getPassword()).isEqualTo(encodedPassword);
    }

    @Test
    public void Given_Username_When_FindCards_Then_ReturnUserCardDtoList() {
        //given
        String username = "username";
        List<Card> cards = List.of(
                Card.builder()
                        .title("제목1")
                        .content("내용1")
                        .build(),
                Card.builder()
                        .title("제목2")
                        .content("내용2")
                        .build()
        );

        //when
        when(cardRepository.findByMember_Username(username)).thenReturn(cards);
        List<CardResponse> cardResponses = memberService.findCards(username);

        //then
        assertThat(cardResponses)
                .hasSize(cards.size())
                .zipSatisfy(cards, (cardResponse, card) -> {
                    assertThat(cardResponse.getTitle()).isEqualTo(card.getTitle());
                });
    }
}