package com.example.outsourcingproject.service;

import com.example.outsourcingproject.config.PasswordEncoder;
import com.example.outsourcingproject.dto.user.*;
import com.example.outsourcingproject.entity.User;
import com.example.outsourcingproject.exceptions.UserException;
import com.example.outsourcingproject.jwt.JwtService;
import com.example.outsourcingproject.repository.UserRepository;
import com.example.outsourcingproject.type.UserRole;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String login(UserLoginRequest request) {

        // 1. 아이디 비번 검증 (인증 성공/실패)
        User user = userRepository.findUserByUsername(request.getUsername()).orElseThrow(() -> new UserException("해당 유저를 찾을 수 없습니다."));
        if(passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            // 2. 인증 성공 -> JWT 발급
            String jwt = jwtService.createJwt(user.getUserId());
            return jwt;
        } else {
            // 3. 인증 실패 -> 예외처리
            throw new UserException("비밀번호가 일치하지 않습니다.");
        }

        // TODO 검증에 실패하게 되면 SignatureException 예외 발생 -> 처리?
    }

    public UserResponse signup(UserRequest request) {

        // 존재하는 회원인지 확인 (email)
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new UserException("이미 존재하는 회원입니다.");
        }

        // role(권한) : String -> Enum 변환
        UserRole userRole = UserRole.from(request.getRole());
        // password 암호화 (보안을 위해)
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        // User 생성
        User user = new User(request.getUsername(), encodedPassword, request.getEmail(), request.getName(), userRole);
        // 회원가입한 회원 저장
        User savedUser = userRepository.save(user);
        // 컨트롤러에 넘겨주기
        return new UserResponse(savedUser.getUsername(), savedUser.getEmail(), savedUser.getName(), savedUser.getRole());
    }

    public UserResponse getUser(Long id) {

        User user = userRepository.findById(id).orElseThrow(() -> new UserException("해당 id를 찾을 수 없습니다."));
        return new UserResponse(user.getUsername(), user.getEmail(), user.getName(), user.getRole());
    }

    public List<UserResponse> getAllUsers() {
/*
        List<User> userList = userRepository.findAll();

        List<UserResponse> responses = new ArrayList<>();
        for(User user : userList) {
            UserResponse addUser = new UserResponse(user.getUsername(), user.getEmail(), user.getName(), user.getRole());
            responses.add(addUser);
        }
        return responses;
*/
        return userRepository.findAll().stream()
                .map(user -> new UserResponse(user.getUsername(), user.getEmail(), user.getName(), user.getRole()))
                .collect(Collectors.toList());
    }

    @Transactional
    public UserResponse updateUser(Long id, UserUpdateRequest request) {

        User user = userRepository.findById(id).orElseThrow(() -> new UserException("해당 id를 찾을 수 없습니다."));

        if(request.getEmail() != null && !(request.getEmail().isBlank())) {
            user.updateEmail(request.getEmail());
        }
        if(request.getName() != null && !(request.getName().isBlank())) {
            user.updateName(request.getName());
        }

        //@Transactional -> userRepository.save(user); 반영

        return new UserResponse(user.getUsername(), user.getEmail(), user.getName(), user.getRole());
    }

    @Transactional
    public void deleteUser(UserDeleteRequest request) {

        User user = userRepository.findUserByUsername(request.getUsername()).orElseThrow(() -> new UserException("해당 유저 아이디를 찾을 수 없습니다."));

        if(passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            userRepository.deleteByUsername(user.getUsername());
        } else {
            throw new UserException("비밀번호가 일치하지 않습니다.");
        }
        // TODO 이미 탈퇴한 회원 -> 예외처리
        // TODO 탈퇴한 사용자의 이메일은 재사용 불가, 복구 불가
        // TODO 탈퇴 시 해당 사용자가 담당한 태스크는 미할당 상태로 변경
    }

}
