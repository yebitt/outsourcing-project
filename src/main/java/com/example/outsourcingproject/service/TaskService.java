package com.example.outsourcingproject.service;

import com.example.outsourcingproject.dto.task.TaskRequest;
import com.example.outsourcingproject.dto.task.TaskResponse;
import com.example.outsourcingproject.entity.Task;
import com.example.outsourcingproject.entity.User;
import com.example.outsourcingproject.exceptions.UserException;
import com.example.outsourcingproject.repository.TaskRepository;
import com.example.outsourcingproject.repository.UserRepository;
import com.example.outsourcingproject.type.Priority;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskResponse createTask(TaskRequest request) {

        // string -> enum 타입 변환
        Priority priority = Priority.fromString(request.getPriority());

        // assigneeId로 User엔티티를 조회해서
        // 1. 담당자가 존재하는 지 확인
        User assignee = userRepository.findById(request.getAssigneeId()).orElseThrow(() -> new UserException("해당 유저가 존재하지 않습니다."));

        // 2. 존재하면, 리포지토리 저장 및 반환
        Task task = new Task(request.getTitle(), request.getDescription(), priority, request.getDueDate(), assignee);
        Task savedTask = taskRepository.save(task);
        return new TaskResponse(savedTask);
    }
}
