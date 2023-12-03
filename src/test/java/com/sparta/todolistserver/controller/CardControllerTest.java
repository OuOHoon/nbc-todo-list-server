package com.sparta.todolistserver.controller;

import com.sparta.todolistserver.config.auth.UserDetailsImpl;
import com.sparta.todolistserver.domain.Member;
import com.sparta.todolistserver.request.card.CardCreateRequest;
import com.sparta.todolistserver.response.card.CardDetailResponse;
import com.sparta.todolistserver.service.CardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CardController.class)
class CardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CardService cardService;

    @BeforeEach
    void setUp() {
        UserDetailsImpl testUserDetails = new UserDetailsImpl(Member.builder()
                .username("user")
                .password("password")
                .build());

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                testUserDetails, testUserDetails.getPassword(), testUserDetails.getAuthorities()));
    }

    @Test
    public void createCardTest() throws Exception {
        //given
        given(cardService.create(any(CardCreateRequest.class), anyString()))
                .willReturn(new CardDetailResponse("title", "content",
                        "author", LocalDate.of(2022, 10, 10)));

        //when + then 응답 검증은 Dto 단위 테스트에서 이미 했으니 넘어간다
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/cards").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"title\",\"content\":\"content\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void findOneTest() throws Exception {
        //given
        given(cardService.findOne(anyLong()))
                .willReturn(new CardDetailResponse("title", "content",
                        "author", LocalDate.of(2022, 10, 10)));

        //when + then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/cards/{id}", 1L).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // 컨트롤러 단위 테스트는 응답 코드 빼고는 이미 다 검증된 내용들이다..
    // 그렇다고 dto의 값 검증 테스트를 컨트롤러에서 하면 중복 테스트가 많아진다..
    // 컨트롤러는 건너뛰고 통합 테스트에 집중하는건 어떨까
}