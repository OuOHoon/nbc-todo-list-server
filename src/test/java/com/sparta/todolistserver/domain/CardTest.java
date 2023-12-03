package com.sparta.todolistserver.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class CardTest {

    @Test
    public void updateCardTest() throws Exception {
        //given
        Card card = Card.builder()
                .title("title")
                .content("content")
                .build();
        String updateTitle = "updateTitle";
        String updateContent = "updateContent";

        //when
        card.updateCard("updateTitle", "updateContent");

        //then
        assertThat(card.getTitle()).isEqualTo(updateTitle);
        assertThat(card.getContent()).isEqualTo(updateContent);
    }

    @Test
    public void setMemberTest() throws Exception {
        //given
        Card card = Card.builder()
                .title("title")
                .content("content")
                .build();
        Member member = new Member();

        //when
        card.setMember(member);

        //then
        assertThat(card.getMember()).isSameAs(member);
    }

    @Test
    public void setFinishTest() throws Exception {
        //given
        Card card = Card.builder()
                .title("title")
                .content("content")
                .build();

        //when
        card.setFinish();

        //then
        assertThat(card.getIsFinished()).isTrue();
    }
}