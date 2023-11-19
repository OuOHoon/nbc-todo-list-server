package com.sparta.todolistserver.service;

import com.sparta.todolistserver.domain.Card;
import com.sparta.todolistserver.domain.Comment;
import com.sparta.todolistserver.domain.Member;
import com.sparta.todolistserver.exception.InvalidUserException;
import com.sparta.todolistserver.exception.NotExistCardException;
import com.sparta.todolistserver.exception.NotExistCommentException;
import com.sparta.todolistserver.exception.NotExistUsernameException;
import com.sparta.todolistserver.repository.CardRepository;
import com.sparta.todolistserver.repository.CommentRepository;
import com.sparta.todolistserver.repository.MemberRepository;
import com.sparta.todolistserver.request.comment.CommentCreateRequest;
import com.sparta.todolistserver.request.comment.CommentUpdateRequest;
import com.sparta.todolistserver.response.comment.CommentResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final CardRepository cardRepository;

    public CommentService(CommentRepository commentRepository, MemberRepository memberRepository, CardRepository cardRepository) {
        this.commentRepository = commentRepository;
        this.memberRepository = memberRepository;
        this.cardRepository = cardRepository;
    }

    public CommentResponse create(String username, Long cardId, CommentCreateRequest request) {
        Member member = memberRepository.findMemberByUsername(username).orElseThrow(NotExistUsernameException::new);
        Card card = cardRepository.findById(cardId).orElseThrow(NotExistCardException::new);
        Comment comment = Comment.builder().content(request.getContent()).build();
        comment.setMember(member);
        comment.setCard(card);
        commentRepository.save(comment);
        return toResponse(comment);
    }

    @Transactional
    public CommentResponse update(String username, Long commentId, CommentUpdateRequest request) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(NotExistCommentException::new);
        if (!comment.getMember().getUsername().equals(username)) {
            throw new InvalidUserException();
        }
        comment.updateContent(request.getContent());
        return toResponse(comment);
    }

    // 올바른 유저인지 체크하는 코드가 중복이네. AOP로 빼기? AOP로 빼면 어떤 메서드에 적용할 지 하나씩 해야하나? 그럼 메서드보다 괜히 더 귀찮은거 아닌가..
    // 관심사 분리는 확실한 장점인데 추가할 코드가 많네..
    // 메서드로 빼기? 메서드로 빼면 memberservice로..? 간단하긴 하다
    public void remove(String username, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(NotExistCommentException::new);
        if (!comment.getMember().getUsername().equals(username)) {
            throw new InvalidUserException();
        }
        commentRepository.delete(comment);
    }

    private CommentResponse toResponse(Comment comment) {
        return CommentResponse.builder()
                .content(comment.getContent()).build();
    }
}
