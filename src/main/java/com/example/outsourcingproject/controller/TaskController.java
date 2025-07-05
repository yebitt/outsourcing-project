package com.example.outsourcingproject.controller;

import com.example.outsourcingproject.dto.task.*;
import com.example.outsourcingproject.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    // TODO 인증이 필요한 API에 적용 : ex. 게시물 생성 - 인증된 회원만 게시글을 생성할 수 있기 때문에 token 이 필요

    // create
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody @Validated TaskRequest request) {
        // TODO userId 인증인가 처리
        Long userId = 1L;

        TaskResponse response = taskService.createTask(userId, request);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    // read : 전체 조회 - (페이징 처리)??
    @GetMapping
    public ResponseEntity<List<TaskResponse>> getTasks() {

        List<TaskResponse> responses = taskService.getTasks();
        return ResponseEntity.ok(responses);
    }

    // read : 단건 조회 - (검색 기능)

    // update
    @PatchMapping("/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long taskId, @RequestBody @Validated TaskUpdateRequest request) {

        TaskResponse response = taskService.updateTask(taskId, request);
        return ResponseEntity.ok(response);
    }

    // update : status 수정 api
    @PatchMapping("/status/{taskId}")
    public ResponseEntity<TaskUpdateStatusResponse> updateStatus(@PathVariable Long taskId) {
        // TODO - 권한이 없는 사용자가 상태를 변경하려고 할 때 에러 발생
        //      - 예: 일반 사용자가 다른 사용자의 태스크 상태를 변경하려는 경우
        TaskUpdateStatusResponse response = taskService.updateStatus(taskId);
        return ResponseEntity.ok(response);
    }

    // delete
    @DeleteMapping("/{taskId}")
    public ResponseEntity<TaskDeleteResponse> deleteTask(@PathVariable Long taskId) {
        // TODO 인가 적용하기
        Long userId = 1L;

        TaskDeleteResponse response = taskService.deleteTask(userId, taskId);
        return ResponseEntity.ok(response);
    }
}
