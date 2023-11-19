package com.sparta.todolistserver.config.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.todolistserver.exception.LoginException;
import com.sparta.todolistserver.request.auth.LoginRequest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j(topic = "UsernamePasswordAuthFilter")
public class UsernamePasswordAuthFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtil jwtUtil;

    public UsernamePasswordAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/auth");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            log.info("Jwt 필터 인증 시작");
            LoginRequest loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
            log.info("login 정보 {}, {}", loginRequest.getUsername(), loginRequest.getPassword());
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
        } catch (IOException e) {
            throw new LoginException();
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        log.info("인증 성공");
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        String token = jwtUtil.createToken(username);
        jwtUtil.addJwtToCookie(token, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        log.info("인증 실패");
        throw new LoginException();
    }
}
