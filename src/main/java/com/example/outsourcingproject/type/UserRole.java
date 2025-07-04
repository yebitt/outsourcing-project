package com.example.outsourcingproject.type;

import com.example.outsourcingproject.exceptions.UserException;

public enum UserRole {
    USER("user"),
    ADMIN("admin");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    // String role -> enum타입으로 변환
    public static UserRole from(String input) {

        for(UserRole userRole : UserRole.values()) {
            if(userRole.role.equalsIgnoreCase(input)) { // 대소문자 무시하고 비교
                return userRole;
            }
        }
        throw new UserException("admin, user 중 입력해야 합니다");
    }
}