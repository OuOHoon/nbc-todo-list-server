package com.sparta.todolistserver.config;


import com.sparta.todolistserver.config.auth.JwtAuthFilter;
import com.sparta.todolistserver.config.auth.JwtUtil;
import com.sparta.todolistserver.config.auth.UserDetailsServiceImpl;
import com.sparta.todolistserver.config.auth.UsernamePasswordAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
@EnableJpaAuditing
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(JwtUtil jwtUtil, AuthenticationConfiguration authenticationConfiguration, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.authenticationConfiguration = authenticationConfiguration;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public UsernamePasswordAuthFilter usernamePasswordAuthFilter() throws Exception {
        UsernamePasswordAuthFilter filter = new UsernamePasswordAuthFilter(jwtUtil);
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return filter;
    }

    @Bean
    public JwtAuthFilter jwtAuthFilter() {
        return new JwtAuthFilter(jwtUtil, userDetailsService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable).cors(AbstractHttpConfigurer::disable) // 테스트 해야해서 csrf cors 꺼둠
                .headers(headers -> // h2 console 테스트 해야해서 x-frame-options 꺼둠
                        headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
                );

        http.sessionManagement((sessionManagement)
                -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests((request) -> {
            request
                .requestMatchers(new AntPathRequestMatcher("/members", "POST")).permitAll() // 회원가입
                .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll() // h2 콘솔
                .requestMatchers(new AntPathRequestMatcher("/cards/**", "GET")).permitAll() // 카드 조회
                .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).permitAll() // 스웨거
                .requestMatchers(new AntPathRequestMatcher("/v3/api-docs/**")).permitAll() // 스웨거
                .anyRequest().authenticated();
        });

        http.addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthFilter.class);
        http.addFilterBefore(usernamePasswordAuthFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
