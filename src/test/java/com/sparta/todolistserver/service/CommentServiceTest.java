package com.sparta.todolistserver.service;

import com.sparta.todolistserver.domain.Card;
import com.sparta.todolistserver.domain.Comment;
import com.sparta.todolistserver.domain.Member;
import com.sparta.todolistserver.repository.CardRepository;
import com.sparta.todolistserver.repository.CommentRepository;
import com.sparta.todolistserver.repository.MemberRepository;
import com.sparta.todolistserver.request.comment.CommentCreateRequest;
import com.sparta.todolistserver.response.comment.CommentResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private CommentService commentService;

    @Test
    public void Given_CommentCreateRequest_When_Create_Then_ReturnCommentResponse() {
        //given
        String username = "user";
        Long cardId = 1L;
        CommentCreateRequest request = new CommentCreateRequest("content");
        Member member = new Member();
        Card card = new Card();
        Comment comment = Comment.builder().content("content").build();
        when(memberRepository.findMemberByUsername(username))
                .thenReturn(Optional.of(member));
        when(cardRepository.findById(cardId))
                .thenReturn(Optional.of(card));
        when(commentRepository.save(any(Comment.class)))
                .thenReturn(comment);

        //when
        CommentResponse response = commentService.create(username, cardId, request);

        //then
        assertThat(response.getContent()).isEqualTo("content");
    }
}