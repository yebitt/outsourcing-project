package com.example.outsourcingproject.dto.user;

import com.example.outsourcingproject.type.UserRole;
import lombok.Getter;

@Getter
public class UserResponse {
    private final String username;
    private final String email;
    private final String name;
    private final UserRole role;

    public UserResponse(String username, String email, String name, UserRole role) {
        this.username = username;
        this.email = email;
        this.name = name;
        this.role = role;
    }
}
