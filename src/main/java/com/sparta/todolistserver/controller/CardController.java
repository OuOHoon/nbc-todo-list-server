package com.sparta.todolistserver.controller;

import com.sparta.todolistserver.config.auth.UserDetailsImpl;
import com.sparta.todolistserver.request.card.CardCreateRequest;
import com.sparta.todolistserver.request.card.CardUpdateRequest;
import com.sparta.todolistserver.response.BaseResponse;
import com.sparta.todolistserver.response.card.CardDetailResponse;
import com.sparta.todolistserver.service.CardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cards")
@Slf4j(topic = "CardController")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping
    public ResponseEntity<CardDetailResponse> create(@RequestBody CardCreateRequest request,
                                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("create {}", request.getTitle());
        CardDetailResponse card = cardService.create(request, userDetails.getUsername());
        return new ResponseEntity<>(card, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardDetailResponse> findOne(@PathVariable Long id) {
        log.info("findOne {}", id);
        CardDetailResponse card = cardService.findOne(id);
        return new ResponseEntity<>(card, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CardDetailResponse> updateOne(@PathVariable Long id,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                        @RequestBody CardUpdateRequest request) {
        log.info("updateOne {}", id);
        CardDetailResponse card = cardService.updateOne(id, userDetails.getUsername(), request);
        return new ResponseEntity<>(card, HttpStatus.OK);
    }

    @PostMapping("/{id}/finish")
    public ResponseEntity<BaseResponse> updateFinish(@PathVariable Long id,
                                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("updateFinish {}", id);
        cardService.updateFinish(id, userDetails.getUsername());
        return new ResponseEntity<>(BaseResponse.of("update finish", 200), HttpStatus.OK);
    }


}
