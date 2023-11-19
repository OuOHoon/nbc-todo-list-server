package com.sparta.todolistserver.controller;

import com.sparta.todolistserver.request.MemberCreateRequest;
import com.sparta.todolistserver.response.BaseResponse;
import com.sparta.todolistserver.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
