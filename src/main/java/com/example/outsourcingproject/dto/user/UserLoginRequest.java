package com.example.outsourcingproject.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserLoginRequest {
    @NotBlank(message = "공백/null은 입력 불가합니다.")
    private String username;

    @NotBlank(message = "공백/null은 입력 불가합니다.")
    @Size(min = 8, message = "최소 8글자 이상이어야 합니다")
    private String password;
}
