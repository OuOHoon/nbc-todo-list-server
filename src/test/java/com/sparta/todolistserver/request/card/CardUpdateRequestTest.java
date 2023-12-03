package com.sparta.todolistserver.request.card;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@JsonTest
class CardUpdateRequestTest {

    @Autowired
    private JacksonTester<CardUpdateRequest> json;

    @Test
    @DisplayName("Object->JsonString")
    public void testSerialize() throws Exception {
        //given
        CardUpdateRequest cardUpdateRequest = new CardUpdateRequest("title", "content");

        //when + then
        assertThat(json.write(cardUpdateRequest))
                .extractingJsonPathStringValue("@.title")
                .isEqualTo("title");
        assertThat(json.write(cardUpdateRequest))
                .extractingJsonPathStringValue("@.content")
                .isEqualTo("content");
    }

    @Test
    @DisplayName("JsonString->Object")
    public void testDeserialize() throws Exception {
        //given
        String jsonString = "{\"title\":\"title\",\"content\":\"content\"}";
        CardUpdateRequest cardUpdateRequest = new CardUpdateRequest("title", "content");

        //when + then
        assertThat(json.parseObject(jsonString).getTitle())
                .isEqualTo(cardUpdateRequest.getTitle());
        assertThat(json.parseObject(jsonString).getContent())
                .isEqualTo(cardUpdateRequest.getContent());
    }
}