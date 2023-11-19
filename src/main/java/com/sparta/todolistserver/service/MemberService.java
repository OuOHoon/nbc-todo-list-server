package com.sparta.todolistserver.service;

import com.sparta.todolistserver.domain.Member;
import com.sparta.todolistserver.exception.DuplicateUsernameException;
import com.sparta.todolistserver.repository.CardRepository;
import com.sparta.todolistserver.repository.MemberRepository;
import com.sparta.todolistserver.request.member.MemberCreateRequest;
import com.sparta.todolistserver.response.card.CardResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final CardRepository cardRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, CardRepository cardRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.cardRepository = cardRepository;
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

    public List<CardResponse> findCards(String username) {
        return cardRepository.findByMember_Username(username).stream()
                .map((card) -> CardService.toResponse(username, card)).toList();
    }

//    public CardDetailResponse updateCard(Long id) {
//        retur
//    }

    private Member toEntity(MemberCreateRequest request) {
        return Member.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
    }
}
