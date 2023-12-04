package com.sparta.todolistserver.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.todolistserver.request.auth.LoginRequest;
import com.sparta.todolistserver.request.card.CardCreateRequest;
import com.sparta.todolistserver.request.comment.CommentCreateRequest;
import com.sparta.todolistserver.request.comment.CommentUpdateRequest;
import com.sparta.todolistserver.request.member.MemberCreateRequest;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    private Cookie authorization;

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
        authorization = login();
    }

    @Test
    @Order(2)
    public void createCardTest() throws Exception {
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

    @Test
    @Order(4)
    public void updateCommentTest() throws Exception {
        //given
        CommentUpdateRequest request = new CommentUpdateRequest("updateContent");

        //when + then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/cards/{cardId}/comments/{commentId}", 1L, 1L).with(csrf())
                        .cookie(authorization)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("updateContent"))
                .andDo(print());
    }
}
