package com.sparta.todolistserver.exception;

public class InvalidUserException extends RuntimeException {
    private static final String MESSAGE = "유효한 사용자가 아닙니다.";

    public InvalidUserException() {
        super(MESSAGE);
    }
}
