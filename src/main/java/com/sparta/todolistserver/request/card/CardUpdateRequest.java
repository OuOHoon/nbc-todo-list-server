package com.sparta.todolistserver.request.card;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CardUpdateRequest {

    private String title;
    private String content;
}
