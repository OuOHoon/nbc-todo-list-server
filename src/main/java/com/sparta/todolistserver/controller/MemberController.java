package com.sparta.todolistserver.controller;

import com.sparta.todolistserver.request.member.MemberCreateRequest;
import com.sparta.todolistserver.response.BaseResponse;
import com.sparta.todolistserver.response.card.CardResponse;
import com.sparta.todolistserver.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<BaseResponse> create(@Valid @RequestBody MemberCreateRequest request) {
        memberService.create(request);
        return new ResponseEntity<>(BaseResponse.of("member created", 201), HttpStatus.CREATED);
    }

    @GetMapping("/{username}/cards")
    public ResponseEntity<List<CardResponse>> findCards(@PathVariable String username) {
        List<CardResponse> cards = memberService.findCards(username);
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }
}
