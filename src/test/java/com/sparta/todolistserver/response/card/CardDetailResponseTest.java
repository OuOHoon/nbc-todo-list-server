package com.sparta.todolistserver.response.card;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@ActiveProfiles("test")
class CardDetailResponseTest {

    @Autowired
    private JacksonTester<CardDetailResponse> json;

    @Test
    @DisplayName("Object->JsonString")
    public void testSerialize() throws Exception {
        //given
        CardDetailResponse cardDetailResponse =
                new CardDetailResponse(
                        "title", "content",
                        "author", LocalDate.of(2022, 10, 10)
                );

        //when + then
        assertThat(json.write(cardDetailResponse))
                .extractingJsonPathStringValue("@.title")
                .isEqualTo("title");
        assertThat(json.write(cardDetailResponse))
                .extractingJsonPathStringValue("@.content")
                .isEqualTo("content");
        assertThat(json.write(cardDetailResponse))
                .extractingJsonPathStringValue("@.author")
                .isEqualTo("author");
        assertThat(json.write(cardDetailResponse))
                .extractingJsonPathStringValue("@.createdAt")
                .isEqualTo("2022-10-10");
    }

    @Test
    @DisplayName("JsonString->Object")
    public void testDeserialize() throws Exception {
        //given
        String jsonString = "{\"title\":\"title\",\"content\":\"content\"," +
                "\"author\":\"author\",\"createdAt\":\"2022-10-10\"}";
        CardDetailResponse cardDetailResponse =
                new CardDetailResponse("title", "content",
                        "author", LocalDate.of(2022, 10, 10)
                );

        //when + then
        assertThat(json.parseObject(jsonString).getTitle())
                .isEqualTo(cardDetailResponse.getTitle());
        assertThat(json.parseObject(jsonString).getContent())
                .isEqualTo(cardDetailResponse.getContent());
        assertThat(json.parseObject(jsonString).getAuthor())
                .isEqualTo(cardDetailResponse.getAuthor());
        assertThat(json.parseObject(jsonString).getCreatedAt())
                .isEqualTo(cardDetailResponse.getCreatedAt());
    }

}