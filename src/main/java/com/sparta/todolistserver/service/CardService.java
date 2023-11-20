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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CardService {

    private final CardRepository cardRepository;
    private final MemberRepository memberRepository;

    public CardService(CardRepository cardRepository, MemberRepository memberRepository) {
        this.cardRepository = cardRepository;
        this.memberRepository = memberRepository;
    }

    public CardDetailResponse create(CardCreateRequest request, String username) {
        Card card = toEntity(request);
        Member member = memberRepository.findMemberByUsername(username).orElseThrow(NotExistUsernameException::new);
        card.setMember(member);
        return toDetailResponse(cardRepository.save(card));
    }

    public CardDetailResponse findOne(Long id) {
        return toDetailResponse(cardRepository.findById(id).orElseThrow(NotExistCardException::new));
    }

    @Transactional
    public CardDetailResponse updateOne(Long id, String username, CardUpdateRequest request) {
        Card card = cardRepository.findById(id).orElseThrow(NotExistCardException::new);
        if (!card.getMember().getUsername().equals(username)) {
            throw new InvalidUserException();
        }
        card.updateCard(request.getTitle(), request.getContent());
        return toDetailResponse(card);
    }

    @Transactional
    public void updateFinish(Long id, String username) {
        Card card = cardRepository.findById(id).orElseThrow(NotExistCardException::new);
        if (!card.getMember().getUsername().equals(username)) {
            throw new InvalidUserException();
        }
        card.updateFinish();
    }

    public Map<String, List<CardResponse>> findAllOrderByCreatedAtDesc() {
        List<Card> cards = cardRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        Map<String, List<CardResponse>> result = new HashMap<>();
        cards.forEach((card) -> {
            List<CardResponse> list = result.getOrDefault(card.getMember().getUsername(), new ArrayList<>());
            list.add(toResponse(card.getMember().getUsername(), card));
            result.put(card.getMember().getUsername(), list);
        });
        return result;
    }

    public List<CardResponse> findCardsByTitle(String title) {
        List<Card> cards = cardRepository.findByTitleContains(title);
        return cards.stream().map(card -> toResponse(card.getMember().getUsername(), card)).toList();
    }

    private Card toEntity(CardCreateRequest request) {
        return Card.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();
    }

    private CardDetailResponse toDetailResponse(Card card) {
        return CardDetailResponse.builder()
                .title(card.getTitle())
                .content(card.getContent())
                .author(card.getMember().getUsername())
                .createdAt(card.getCreatedAt())
                .build();
    }

    public static CardResponse toResponse(String username, Card card){
        return CardResponse.builder()
                .title(card.getTitle())
                .author(username)
                .isFinish(card.getIsFinish())
                .createdAt(card.getCreatedAt())
                .build();
    }
}
