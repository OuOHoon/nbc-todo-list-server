package com.sparta.todolistserver.repository;

import com.sparta.todolistserver.domain.Card;
import com.sparta.todolistserver.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CardRepositoryTest extends BaseRepositoryTest{

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void findByMember_UsernameTest() throws Exception {
        //given
        String username = "user";
        Member member = Member.builder().username("user").build();
        Card card = Card.builder().title("title").build();
        card.setMember(member);
        memberRepository.save(member);
        cardRepository.save(card);

        //when
        List<Card> userCards = cardRepository.findByMember_Username(username);

        //then
        assertThat(userCards.size()).isEqualTo(1);
    }

    @Test
    public void findByTitleContainsTest() throws Exception {
        //given
        Card card = Card.builder().title("title1").build();
        Card card2 = Card.builder().title("title2").build();
        String searchTitle = "1";
        cardRepository.save(card);
        cardRepository.save(card2);

        //when
        List<Card> searchCards = cardRepository.findByTitleContains(searchTitle);

        //then
        for (Card searchCard : searchCards) {
            assertThat(searchCard.getTitle().contains(searchTitle)).isTrue();
        }
    }
}