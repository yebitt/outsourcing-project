package com.example.outsourcingproject.dto.common;

import lombok.Getter;

@Getter
public class UserAssigneeResponse {

    private final Long id;
    private final String username;
    private final String name;
    private final String email;

    public UserAssigneeResponse(Long id, String username, String name, String email) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.email = email;
    }
}
