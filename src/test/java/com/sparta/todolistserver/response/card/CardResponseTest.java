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
class CardResponseTest {

    @Autowired
    private JacksonTester<CardResponse> json;

    @Test
    @DisplayName("Object->JsonString")
    public void testSerialize() throws Exception {
        //given
        CardResponse cardResponse =
                new CardResponse(
                        "title", "author",
                        LocalDate.of(2022, 10, 10), Boolean.FALSE
                );

        //when + then
        assertThat(json.write(cardResponse))
                .extractingJsonPathStringValue("@.title")
                .isEqualTo("title");
        assertThat(json.write(cardResponse))
                .extractingJsonPathStringValue("@.author")
                .isEqualTo("author");
        assertThat(json.write(cardResponse))
                .extractingJsonPathStringValue("@.createdAt")
                .isEqualTo("2022-10-10");
        assertThat(json.write(cardResponse))
                .extractingJsonPathBooleanValue("@.isFinished")
                .isEqualTo(Boolean.FALSE);
    }

    @Test
    @DisplayName("JsonString->Object")
    public void testDeserialize() throws Exception {
        //given
        String jsonString = "{\"title\":\"title\",\"author\":\"author\"," +
                "\"createdAt\":\"2022-10-10\",\"isFinished\":\"false\"}";
        CardResponse cardResponse =
                new CardResponse(
                        "title", "author",
                        LocalDate.of(2022, 10, 10), Boolean.FALSE
                );

        //when + then
        assertThat(json.parseObject(jsonString).getTitle())
                .isEqualTo(cardResponse.getTitle());
        assertThat(json.parseObject(jsonString).getAuthor())
                .isEqualTo(cardResponse.getAuthor());
        assertThat(json.parseObject(jsonString).getCreatedAt())
                .isEqualTo(cardResponse.getCreatedAt());
        assertThat(json.parseObject(jsonString).getIsFinished())
                .isEqualTo(cardResponse.getIsFinished());
    }

}