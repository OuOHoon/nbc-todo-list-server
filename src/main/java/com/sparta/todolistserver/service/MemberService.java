package com.sparta.todolistserver.service;

import com.sparta.todolistserver.domain.Member;
import com.sparta.todolistserver.exception.DuplicateUsernameException;
import com.sparta.todolistserver.repository.MemberRepository;
import com.sparta.todolistserver.request.MemberCreateRequest;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void create(MemberCreateRequest request) {
        try {
            Member member = toEntity(request);
            memberRepository.save(member);
        } catch (ConstraintViolationException exception) {
            throw new DuplicateUsernameException("중복된 username 입니다.");
        }
    }

    private Member toEntity(MemberCreateRequest request) {
        return Member.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .build();
    }
}
