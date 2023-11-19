package com.sparta.todolistserver.service;

import com.sparta.todolistserver.domain.Member;
import com.sparta.todolistserver.exception.DuplicateUsernameException;
import com.sparta.todolistserver.repository.MemberRepository;
import com.sparta.todolistserver.request.MemberCreateRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void create(MemberCreateRequest request) {
        try {
            Member member = toEntity(request);
            memberRepository.save(member);
        } catch (Exception e) {
            throw new DuplicateUsernameException();
        }
    }

    private Member toEntity(MemberCreateRequest request) {
        return Member.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
    }
}
