package com.example.outsourcingproject.dto.user;

import com.example.outsourcingproject.exceptions.UserException;
import com.example.outsourcingproject.type.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserRequest {
    @NotBlank(message = "공백/null은 입력 불가합니다.")
    private String username;

    @NotBlank(message = "공백/null은 입력 불가합니다.")
    @Size(min = 8, message = "최소 8글자 이상이어야 합니다")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).+$",
            message = "비밀번호는 대소문자 영문, 숫자, 특수문자를 포함해야 합니다."
    )
    private String password;

    @NotBlank(message = "공백/null은 입력 불가합니다.")
    @Email(message = "이메일 형식이어야 합니다.")
    private String email;

    @NotBlank(message = "공백/null은 입력 불가합니다.")
    private String name;

    @NotBlank(message = "공백/null은 입력 불가합니다.")
    private String role;
}
