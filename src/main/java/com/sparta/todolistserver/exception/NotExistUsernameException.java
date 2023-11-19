package com.sparta.todolistserver.exception;

public class NotExistUsernameException extends RuntimeException{
    private static final String MESSAGE = "존재하지 않는 username 입니다.";
    public NotExistUsernameException() {
        super(MESSAGE);
    }
}
