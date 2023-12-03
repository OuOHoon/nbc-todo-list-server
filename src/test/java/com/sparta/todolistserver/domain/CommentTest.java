package com.sparta.todolistserver.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CommentTest {

    @Test
    public void updateCommentTest() throws Exception {
        //given
        Comment comment = Comment.builder()
                .content("content")
                .build();
        String updateContent = "updateContent";
        
        //when
        comment.updateComment(updateContent);
        
        //then
        assertThat(comment.getContent()).isEqualTo(updateContent);
    }
    
    @Test
    public void setMemberTest() throws Exception {
        //given
        Comment comment = Comment.builder()
                .content("content")
                .build();
        Member member = new Member();
        
        //when
        comment.setMember(member);
        
        //then
        assertThat(comment.getMember()).isSameAs(member);
    }
    
    @Test
    public void setCardTest() throws Exception {
        //given
        Comment comment = Comment.builder()
                .content("content")
                .build();
        Card card = new Card();
        
        //when
        comment.setCard(card);
        
        //then
        assertThat(comment.getCard()).isSameAs(card);
    }
}