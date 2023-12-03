package com.sparta.todolistserver.request.comment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@ActiveProfiles("test")
class CommentCreateRequestTest {

    @Autowired
    private JacksonTester<CommentCreateRequest> json;

    @Test
    @DisplayName("Object->JsonString")
    public void testSerialize() throws Exception {
        //given
        CommentCreateRequest commentCreateRequest = new CommentCreateRequest("content");

        //when + then
        assertThat(json.write(commentCreateRequest))
                .extractingJsonPathStringValue("@.content")
                .isEqualTo("content");
    }

    @Test
    @DisplayName("JsonString->Object")
    public void testDeserialize() throws Exception {
        //given
        String jsonString = "{\"content\":\"content\"}";
        CommentCreateRequest commentCreateRequest = new CommentCreateRequest("content");

        //when + then
        assertThat(json.parseObject(jsonString).getContent())
                .isEqualTo(commentCreateRequest.getContent());
    }
}