package com.example.outsourcingproject.type;

public enum Status {
    TODO,
    IN_PROGRESS,
    DONE;

    // String -> enum타입으로 변환
    public static Status fromString(String value) {
        try {
            return Status.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException("Status는 TODO, IN_PROGRESS, DONE 중 하나여야 합니다.");
        }
    }
}
