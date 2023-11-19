package com.sparta.todolistserver.exception;

public class DuplicateUsernameException extends RuntimeException {
    private static final String MESSAGE = "중복된 username 입니다.";
    public DuplicateUsernameException() {
        super(MESSAGE);
    }
}
