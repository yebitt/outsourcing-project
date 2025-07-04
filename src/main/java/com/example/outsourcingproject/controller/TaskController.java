package com.example.outsourcingproject.controller;

import com.example.outsourcingproject.dto.task.TaskRequest;
import com.example.outsourcingproject.dto.task.TaskResponse;
import com.example.outsourcingproject.dto.task.TaskUpdateRequest;
import com.example.outsourcingproject.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    // TODO 인증이 필요한 API에 적용 : ex. 게시물 생성 - 인증된 회원만 게시글을 생성할 수 있기 때문에 token 이 필요

    // create
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody @Validated TaskRequest request) {

        TaskResponse response = taskService.createTask(request);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    // read : 전체 조회

    // read : 단건 조회

//    // update
//    @PatchMapping("/{id}")
//    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long id, @RequestBody @Validated TaskUpdateRequest request) {
//
//    }

    // update : status 수정 api

    // delete
}
