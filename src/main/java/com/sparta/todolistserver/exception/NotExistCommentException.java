package com.sparta.todolistserver.exception;

public class NotExistCommentException extends RuntimeException {

    private static final String MESSAGE = "존재하지 않는 comment id 입니다.";

    public NotExistCommentException() {
        super(MESSAGE);
    }
}
