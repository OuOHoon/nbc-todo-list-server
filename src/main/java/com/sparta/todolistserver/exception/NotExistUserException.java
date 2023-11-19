package com.sparta.todolistserver.exception;

public class NotExistUserException extends RuntimeException{
    private static final String MESSAGE = "회원을 찾을 수 없습니다.";
    public NotExistUserException() {
        super(MESSAGE);
    }
}
