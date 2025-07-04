package com.example.outsourcingproject.dto.user;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import lombok.Getter;

@Getter
public class UserUpdateRequest {
    @Email(message = "이메일 형식이어야 합니다.")
    private String email;
    private String name;

    // @Validated 붙이면 자동 실행됨
    @AssertTrue(message = "하나이상 반드시 수정해야 합니다.")
    public boolean isValidUpdateRequest() {
        return (email != null) || (name != null);
    }
}
