package com.sparta.todolistserver.controller;

import com.sparta.todolistserver.response.BaseResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<BaseResponse> login(HttpServletResponse response) {
        return new ResponseEntity<>(BaseResponse.of("login", 200), HttpStatus.OK);
    }
}
