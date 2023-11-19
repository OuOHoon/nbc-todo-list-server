package com.sparta.todolistserver.response.comment;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommentResponse {

    private String content;
}
