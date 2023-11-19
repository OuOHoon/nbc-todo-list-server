package com.sparta.todolistserver.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class BaseResponse {

    private String message;

    private Integer code;

    public static BaseResponse of(String message, Integer code){
        return new BaseResponse(message, code);
    }
}
