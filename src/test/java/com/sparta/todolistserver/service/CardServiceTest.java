package com.sparta.todolistserver.service;

import com.sparta.todolistserver.domain.Card;
import com.sparta.todolistserver.domain.Member;
import com.sparta.todolistserver.exception.InvalidUserException;
import com.sparta.todolistserver.exception.NotExistCardException;
import com.sparta.todolistserver.exception.NotExistUsernameException;
import com.sparta.todolistserver.repository.CardRepository;
import com.sparta.todolistserver.repository.MemberRepository;
import com.sparta.todolistserver.request.card.CardCreateRequest;
import com.sparta.todolistserver.request.card.CardUpdateRequest;
import com.sparta.todolistserver.response.card.CardDetailResponse;
import com.sparta.todolistserver.response.card.CardResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private CardService cardService;

    @Test
    public void Given_CardCreateRequest_When_Create_Then_ReturnCardDetailResponse() {
        //given
        CardCreateRequest request = new CardCreateRequest("제목", "내용");
        String username = "username";
        Optional<Member> member = Optional.of(
                Member.builder()
                        .username(username)
                        .build());
        when(memberRepository.findMemberByUsername(anyString()))
                .thenReturn(member);
        when(cardRepository.save(any(Card.class)))
                .thenReturn(Card.builder()
                        .title(request.getTitle())
                        .content(request.getContent())
                        .member(member.get())
                        .build());

        //when
        CardDetailResponse cardDetailResponse = cardService.create(request, username);
        ArgumentCaptor<Card> cardCaptor = ArgumentCaptor.forClass(Card.class);
        verify(cardRepository).save(cardCaptor.capture());
        Card savedCard = cardCaptor.getValue();

        //then
        // toEntity 검증
        assertThat(savedCard.getTitle())
                .isEqualTo(request.getTitle());
        assertThat(savedCard.getContent())
                .isEqualTo(request.getContent());
        assertThat(savedCard.getMember().getUsername())
                .isEqualTo(member.get().getUsername());

        // toDetailResponse 검증
        assertThat(savedCard.getTitle())
                .isEqualTo(cardDetailResponse.getTitle());
        assertThat(savedCard.getContent())
                .isEqualTo(cardDetailResponse.getContent());
        assertThat(savedCard.getMember().getUsername())
                .isEqualTo(cardDetailResponse.getAuthor());
    }

    @Test
    public void Given_NotExistUsernameRequest_When_Create_Then_ThrowNotExistUsernameException() {
        //given
        CardCreateRequest request = new CardCreateRequest("제목", "내용");
        String username = "username";
        when(memberRepository.findMemberByUsername(anyString()))
                .thenThrow(new NotExistUsernameException());

        //when
        Throwable throwable = catchThrowable(() -> cardService.create(request, username));

        //then
        assertThat(throwable).isInstanceOf(NotExistUsernameException.class);
    }

    @Test
    public void Given_NotExistCardId_When_FindOne_Then_ThrowNotExistCardException() {
        //given
        Long cardId = 1L;
        Optional<Card> card = Optional.of(Card.builder()
                .title("제목")
                .content("내용")
                .build());
        when(cardRepository.findById(cardId))
                .thenThrow(new NotExistCardException());

        //when
        Throwable throwable = catchThrowable(() -> cardService.findOne(cardId));

        //then
        assertThat(throwable).isInstanceOf(NotExistCardException.class);
    }

    @Test
    public void Given_CardUpdateRequest_When_UpdateOne_Then_ReturnUpdatedCardDetailResponse() {
        //given
        Long cardId = 1L;
        String username = "username";
        CardUpdateRequest request = new CardUpdateRequest("updateTitle", "updateContent");
        Optional<Card> card = Optional.of(Card.builder()
                .title("제목")
                .content("내용")
                .member(Member.builder().username("username").build())
                .build()
        );
        when(cardRepository.findById(anyLong()))
                .thenReturn(card);

        //when
        CardDetailResponse cardDetailResponse =
                cardService.updateOne(cardId, username, request);

        //then
        assertThat(cardDetailResponse.getTitle()).isEqualTo("updateTitle");
        assertThat(cardDetailResponse.getContent()).isEqualTo("updateContent");
    }

    @Test
    public void Given_InvalidUsername_When_UpdateOne_Then_ThrowInvalidUserException() {
        //given
        CardUpdateRequest request = new CardUpdateRequest("updateTitle", "updateContent");
        Optional<Card> card = Optional.of(Card.builder()
                .title("제목")
                .content("내용")
                .member(Member.builder().username("username").build())
                .build()
        );
        when(cardRepository.findById(anyLong()))
                .thenReturn(card);

        //when
        Throwable throwable =
                catchThrowable(() -> cardService.updateOne(1L, "username1", request));

        //then
        assertThat(throwable).isInstanceOf(InvalidUserException.class);
    }

    @Test
    public void When_FindAllOrderByCreatedAtDesc_Then_ReturnResult() {
        //given
        Member member1 = Member.builder().username("user1").build();
        Member member2 = Member.builder().username("user2").build();
        Card card1 = Card.builder().member(member1).build();
        Card card2 = Card.builder().member(member2).build();
        when(cardRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt")))
                .thenReturn(Arrays.asList(card1, card2));

        //when
        Map<String, List<CardResponse>> result = cardService.findAllOrderByCreatedAtDesc();

        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(member1.getUsername()).size()).isEqualTo(1);
        assertThat(result.get(member2.getUsername()).size()).isEqualTo(1);
    }

    @Test
    public void Given_Title_When_FindCardsByTitle_Then_ReturnCardResponseList() {
        //given
        String title = "title";
        List<Card> cards = List.of(
                Card.builder().title("title1")
                        .content("content1")
                        .member(Member.builder().username("username1").build())
                        .build(),
                Card.builder().title("title2")
                        .content("content2")
                        .member(Member.builder().username("username2").build())
                        .build()
        );
        when(cardRepository.findByTitleContains(anyString()))
                .thenReturn(cards);

        //when
        List<CardResponse> findCards = cardService.findCardsByTitle(title);

        //then
        assertThat(findCards)
                .zipSatisfy(cards, (findCard, card) -> {
                    assertThat(findCard.getTitle()).isEqualTo(card.getTitle());
                    assertThat(findCard.getAuthor()).isEqualTo(card.getMember().getUsername());
                });
    }
}