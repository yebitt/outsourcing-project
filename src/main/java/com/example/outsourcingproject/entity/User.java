package com.example.outsourcingproject.entity;

import com.example.outsourcingproject.type.UserRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name", nullable = false, unique = true)
    private String username; // 닉네임이자, 아이디

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    // createdAt, updatedAt

    // 기본 생성자 대신 -> @NoArgsConstructor(access = AccessLevel.PROTECTED) 사용

    public User(String username, String password, String email, String name, UserRole role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.role = role;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updateName(String name) {
        this.name = name;
    }

}
