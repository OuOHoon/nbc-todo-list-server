package com.sparta.todolistserver.response.comment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@ActiveProfiles("test")
class CommentResponseTest {

    @Autowired
    private JacksonTester<CommentResponse> json;

    @Test
    @DisplayName("Object->JsonString")
    public void testSerialize() throws Exception {
        //given
        CommentResponse commentResponse = new CommentResponse("content");

        //when + then
        assertThat(json.write(commentResponse))
                .extractingJsonPathStringValue("@.content")
                .isEqualTo("content");
    }

    @Test
    @DisplayName("JsonString->Object")
    public void testDeserialize() throws Exception {
        //given
        String jsonString = "{\"content\":\"content\"}";
        CommentResponse commentResponse = new CommentResponse("content");

        //when + then
        assertThat(json.parseObject(jsonString).getContent())
                .isEqualTo(commentResponse.getContent());
    }

}