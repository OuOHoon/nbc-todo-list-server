package com.sparta.todolistserver.controller;

import com.sparta.todolistserver.exception.DuplicateUsernameException;
import com.sparta.todolistserver.exception.InvalidUserException;
import com.sparta.todolistserver.exception.NotExistUserException;
import com.sparta.todolistserver.exception.NotExistCardException;
import com.sparta.todolistserver.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse> methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        String errorMessage = exception.getBindingResult().getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining(" "));

        return ResponseEntity.badRequest()
                .body(BaseResponse.of(errorMessage, HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<BaseResponse> duplicateUsernameException(DuplicateUsernameException exception) {
        return ResponseEntity.badRequest()
                .body(BaseResponse.of(exception.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(NotExistCardException.class)
    public ResponseEntity<BaseResponse> notExistCardException(NotExistCardException e) {
        return ResponseEntity.badRequest()
                .body(BaseResponse.of(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<BaseResponse> invalidUserException(InvalidUserException e) {
        return new ResponseEntity<>(BaseResponse.of(e.getMessage(), HttpStatus.UNAUTHORIZED.value()),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NotExistUserException.class)
    public ResponseEntity<BaseResponse> loginException(NotExistUserException e) {
        return ResponseEntity.badRequest()
                .body(BaseResponse.of(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }
}
