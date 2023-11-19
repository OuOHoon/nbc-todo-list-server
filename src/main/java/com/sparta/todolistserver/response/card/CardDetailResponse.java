package com.sparta.todolistserver.response.card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Builder
public class CardDetailResponse {

    private String title;
    private String content;
    private String author;
    private LocalDate createdAt;
}
