package com.sparta.todolistserver.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberCreateRequest {
    @Size(min = 4, max = 10, message = "username은 최소 4자 이상, 10자 이하여야 합니다.")
    @Pattern(regexp = "^[a-z0-9]*$", message = "username은 알파벳 소문자 또는 숫자로 구성되어야 합니다.") // 알파벳 소문자, 숫자만 가능
    private String username;
    @Size(min = 8, max = 15, message = "password는 최소 8자 이상, 15자 이하여야 합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "password는 알파벳 대문자, 소문자 또는 숫자로 구성되어야 합니다.") // 알파벳 대소문자, 숫자만 가능
    private String password;
}
