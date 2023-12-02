package com.sparta.todolistserver.request.member;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class MemberCreateRequestTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    static void setValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Nested
    @DisplayName("Username 테스트")
    class UsernameTest {
        @Test
        public void Given_ValidUsername_When_Validate_Then_NoConstraintViolation() {
            //given
            MemberCreateRequest validRequest =
                    new MemberCreateRequest("username1", "password");
            //when
            Set<ConstraintViolation<MemberCreateRequest>> valid =
                    validator.validateProperty(validRequest, "username");
            //then
            assertThat(valid.size()).isEqualTo(0);
        }

        @Test
        public void Given_InvalidSizeUsername_When_Validate_Then_HasConstraintViolation() {
            //given
            MemberCreateRequest sizeOverRequest =
                    new MemberCreateRequest("usernamesizeover", "password");
            MemberCreateRequest sizeUnderRequest =
                    new MemberCreateRequest("u", "password");
            //when
            Set<ConstraintViolation<MemberCreateRequest>> sizeOver =
                    validator.validateProperty(sizeOverRequest, "username");
            Set<ConstraintViolation<MemberCreateRequest>> sizeUnder =
                    validator.validateProperty(sizeUnderRequest, "username");
            //then
            assertThat(sizeOver.size()).isGreaterThan(0);
            assertThat(sizeUnder.size()).isGreaterThan(0);
        }

        @Test
        public void Given_InvalidPatternUsername_When_Validate_Then_HasConstraintViolation() {
            //given
            MemberCreateRequest upperCaseRequest =
                    new MemberCreateRequest("Invalid", "password");
            MemberCreateRequest notEnglishRequest =
                    new MemberCreateRequest("한글이름", "password");
            //when
            Set<ConstraintViolation<MemberCreateRequest>> upperCase =
                    validator.validateProperty(upperCaseRequest, "username");
            Set<ConstraintViolation<MemberCreateRequest>> notEnglish =
                    validator.validateProperty(notEnglishRequest, "username");
            //then
            assertThat(upperCase.size()).isGreaterThan(0);
            assertThat(notEnglish.size()).isGreaterThan(0);
        }
    }

    @Nested
    @DisplayName("Password 테스트")
    class PasswordTest {

        @Test
        public void Given_ValidPassword_When_Validate_Then_NoConstraintViolation() {
            //given
            MemberCreateRequest validRequest =
                    new MemberCreateRequest("username1", "passworD1");
            //when
            Set<ConstraintViolation<MemberCreateRequest>> valid =
                    validator.validateProperty(validRequest, "password");
            //then
            assertThat(valid.size()).isEqualTo(0);
        }

        @Test
        public void Given_InvalidSizePassword_Then_HasConstraintViolation() {
            //given
            MemberCreateRequest sizeOverRequest =
                    new MemberCreateRequest("username", "password123456789");
            MemberCreateRequest sizeUnderRequest =
                    new MemberCreateRequest("username", "pass");
            //when
            Set<ConstraintViolation<MemberCreateRequest>> sizeOver =
                    validator.validateProperty(sizeOverRequest, "password");
            Set<ConstraintViolation<MemberCreateRequest>> sizeUnder =
                    validator.validateProperty(sizeUnderRequest, "password");
            //then
            assertThat(sizeOver.size()).isGreaterThan(0);
            assertThat(sizeUnder.size()).isGreaterThan(0);
        }

        @Test
        public void Given_InvalidPatternPassword_Then_HasConstraintViolation() {
            //given
            MemberCreateRequest upperCaseRequest =
                    new MemberCreateRequest("username", "password!");
            MemberCreateRequest notEnglishRequest =
                    new MemberCreateRequest("username", "password한글");
            //when
            Set<ConstraintViolation<MemberCreateRequest>> upperCase =
                    validator.validateProperty(upperCaseRequest, "password");
            Set<ConstraintViolation<MemberCreateRequest>> notEnglish =
                    validator.validateProperty(notEnglishRequest, "password");
            //then
            assertThat(upperCase.size()).isGreaterThan(0);
            assertThat(notEnglish.size()).isGreaterThan(0);
        }
    }
}