package com.sparta.todolistserver.request.comment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JsonTest
@ActiveProfiles("test")
class CommentUpdateRequestTest {

    @Autowired
    private JacksonTester<CommentUpdateRequest> json;

    @Test
    @DisplayName("Object->JsonString")
    public void testSerialize() throws Exception {
        //given
        CommentUpdateRequest commentUpdateRequest = new CommentUpdateRequest("content");

        //when + then
        assertThat(json.write(commentUpdateRequest))
                .extractingJsonPathStringValue("@.content")
                .isEqualTo("content");
    }

    @Test
    @DisplayName("JsonString->Object")
    public void testDeserialize() throws Exception {
        //given
        String jsonString = "{\"content\":\"content\"}";
        CommentUpdateRequest commentUpdateRequest = new CommentUpdateRequest("content");

        //when + then
        assertThat(json.parseObject(jsonString).getContent())
                .isEqualTo(commentUpdateRequest.getContent());
    }
}