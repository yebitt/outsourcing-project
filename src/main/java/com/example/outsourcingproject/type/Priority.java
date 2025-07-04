package com.example.outsourcingproject.type;

import com.example.outsourcingproject.exceptions.UserException;

public enum Priority {
    LOW,
    MEDIUM,
    HIGH;

    // String -> enum타입으로 변환
    public static Priority fromString(String value) {
        try {
            return Priority.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException("Priority는 LOW, MEDIUM, HIGH 중 하나여야 합니다.");
        }
    }
}
