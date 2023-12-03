package com.sparta.todolistserver.request.card;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@ActiveProfiles("test")
class CardCreateRequestTest {

    @Autowired
    private JacksonTester<CardCreateRequest> json;

    @Test
    @DisplayName("Object->JsonString")
    public void testSerialize() throws Exception {
        //given
        CardCreateRequest cardCreateRequest = new CardCreateRequest("title1", "content1");

        //when + then
        assertThat(json.write(cardCreateRequest))
                .extractingJsonPathStringValue("@.title")
                .isEqualTo("title1");
        assertThat(json.write(cardCreateRequest))
                .extractingJsonPathStringValue("@.content")
                .isEqualTo("content1");
    }

    @Test
    @DisplayName("JsonString->Object")
    public void testDeserialize() throws Exception {
        //given
        String jsonString = "{\"title\":\"title1\",\"content\":\"content1\"}";
        CardCreateRequest cardCreateRequest = new CardCreateRequest("title1", "content1");
        //when + then
        assertThat(json.parseObject(jsonString).getTitle())
                .isEqualTo(cardCreateRequest.getTitle());
        assertThat(json.parseObject(jsonString).getContent())
                .isEqualTo(cardCreateRequest.getContent());
    }
}