package com.sparta.todolistserver.integration;

import com.sparta.todolistserver.request.auth.LoginRequest;
import com.sparta.todolistserver.request.card.CardCreateRequest;
import com.sparta.todolistserver.request.comment.CommentCreateRequest;
import com.sparta.todolistserver.request.member.MemberCreateRequest;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IntegrationTest extends BaseIntegrationTest {

    // 로그인용 메서드
    public Cookie login() throws Exception {
        //given
        LoginRequest request = new LoginRequest("username", "password");

        //when + then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/login").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();
        return mvcResult.getResponse().getCookie("Authorization");
    }

    @Test
    @Order(1)
    public void createMemberTest() throws Exception {
        //given
        MemberCreateRequest request = new MemberCreateRequest("username", "password");

        //when + then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/members").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("member created"))
                .andDo(print());
    }

    @Test
    @Order(2)
    public void createCardTest() throws Exception {
        Cookie authorization = login();
        //given
        CardCreateRequest request = new CardCreateRequest("title", "content");

        //when + then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/cards").with(csrf())
                        .cookie(authorization)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @Order(3)
    public void createCommentTest() throws Exception {
        Cookie authorization = login();
        //given
        CommentCreateRequest request = new CommentCreateRequest("content");

        //when + then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/cards/{cardId}/comments", 1).with(csrf())
                        .cookie(authorization)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andDo(print());
    }
}
