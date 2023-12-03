package com.sparta.todolistserver.request.auth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;


@JsonTest
@ActiveProfiles("test")
class LoginRequestTest {

    @Autowired
    private JacksonTester<LoginRequest> json;

    @Test
    @DisplayName("Object->JsonString")
    public void testSerialize() throws Exception {
        //given
        LoginRequest loginRequest = new LoginRequest("username1", "password");

        //when + then
        assertThat(json.write(loginRequest))
                .extractingJsonPathStringValue("@.username")
                .isEqualTo("username1");
        assertThat(json.write(loginRequest))
                .extractingJsonPathStringValue("@.password")
                .isEqualTo("password");
    }

    @Test
    @DisplayName("JsonString->Object")
    public void testDeserialize() throws Exception {
        //given
        String jsonString = "{\"username\":\"username1\",\"password\":\"password\"}";
        LoginRequest loginRequest = new LoginRequest("username1", "password");

        //when + then
        assertThat(json.parseObject(jsonString).getUsername())
                .isEqualTo(loginRequest.getUsername());
        assertThat(json.parseObject(jsonString).getPassword())
                .isEqualTo(loginRequest.getPassword());
    }
}