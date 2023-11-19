package com.sparta.todolistserver.exception;

public class NotExistCardException extends RuntimeException {
    private static final String MESSAGE = "존재하지 않는 id 입니다.";

    public NotExistCardException() {
        super(MESSAGE);
    }
}
