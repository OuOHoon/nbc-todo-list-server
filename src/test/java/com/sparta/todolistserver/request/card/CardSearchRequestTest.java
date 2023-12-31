package com.sparta.todolistserver.request.card;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@JsonTest
class CardSearchRequestTest {

    @Autowired
    private JacksonTester<CardSearchRequest> json;

    @Test
    @DisplayName("Object->JsonString")
    public void testSerialize() throws Exception {
        //given
        CardSearchRequest cardSearchRequest = new CardSearchRequest("searchTitle");

        //when + then
        assertThat(json.write(cardSearchRequest))
                .extractingJsonPathStringValue("@.title")
                .isEqualTo("searchTitle");
    }

    @Test
    @DisplayName("JsonString->Object")
    public void testDeserialize() throws Exception {
        //given
        String jsonString = "{\"title\":\"searchTitle\"}";
        CardSearchRequest cardSearchRequest = new CardSearchRequest("searchTitle");

        //when + then
        assertThat(json.parseObject(jsonString).getTitle())
                .isEqualTo(cardSearchRequest.getTitle());
    }
}