package com.sparta.todolistserver.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@ActiveProfiles("test")
class BaseResponseTest {

    @Autowired
    private JacksonTester<BaseResponse> json;

    @Test
    @DisplayName("Object->JsonString")
    public void testSerialize() throws Exception {
        //given
        BaseResponse baseResponse = new BaseResponse("message", 200);

        //when + then
        assertThat(json.write(baseResponse))
                .extractingJsonPathStringValue("@.message")
                .isEqualTo("message");
        assertThat(json.write(baseResponse))
                .extractingJsonPathNumberValue("@.code")
                .isEqualTo(200);
    }

    @Test
    @DisplayName("JsonString->Object")
    public void testDeserialize() throws Exception {
        //given
        String jsonString = "{\"message\":\"message\",\"code\":200}";
        BaseResponse baseResponse = new BaseResponse("message", 200);

        //when + then
        assertThat(json.parseObject(jsonString).getMessage())
                .isEqualTo(baseResponse.getMessage());
        assertThat(json.parseObject(jsonString).getCode())
                .isEqualTo(baseResponse.getCode());
    }

    @Test
    public void ofConstructorTest() throws Exception {
        //given
        String message = "message";
        Integer code = 200;

        //when
        BaseResponse of = BaseResponse.of(message, code);

        //then
        assertThat(of.getMessage()).isEqualTo(message);
        assertThat(of.getCode()).isEqualTo(code);
    }

}