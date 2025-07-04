package com.example.outsourcingproject.controller;

import com.example.outsourcingproject.dto.user.*;
import com.example.outsourcingproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    // 인증(Authentication) : 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Validated UserLoginRequest request) {

        String response = userService.login(request);
        return ResponseEntity.ok("로그인 성공, 발급된 JWT : " + response);
    }

    // create : 회원가입 API
    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(@RequestBody @Validated UserRequest request) {

        UserResponse response = userService.signup(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // read : 유저 단일 조회
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {

        UserResponse response = userService.getUser(id);
        return ResponseEntity.ok(response);
    }

    // read : 유저 목록(전체) 조회
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {

        List<UserResponse> response = userService.getAllUsers();
        return ResponseEntity.ok(response);
    }

    // update
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody @Validated UserUpdateRequest request) {

        UserResponse response = userService.updateUser(id, request);
        return ResponseEntity.ok(response);
    }

    // TODO : userName, password 수정 api 따로 빼기? / 권한에 따라 api 분리?

    // delete : 회원탈퇴
    @DeleteMapping
    public ResponseEntity<String> deleteUser(@RequestBody @Validated UserDeleteRequest request) {

        userService.deleteUser(request);
        return ResponseEntity.ok("삭제가 완료되었습니다.");
    }
}
