package com.sparta.todolistserver.controller;

import com.sparta.todolistserver.exception.DuplicateUsernameException;
import com.sparta.todolistserver.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse> methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return ResponseEntity.badRequest()
                .body(BaseResponse.of(exception.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<BaseResponse> duplicateUsernameException(DuplicateUsernameException exception) {
        return ResponseEntity.badRequest()
                .body(BaseResponse.of(exception.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }
}
