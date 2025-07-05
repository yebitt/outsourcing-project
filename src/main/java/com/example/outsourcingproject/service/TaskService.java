package com.example.outsourcingproject.service;

import com.example.outsourcingproject.dto.task.*;
import com.example.outsourcingproject.entity.Task;
import com.example.outsourcingproject.entity.User;
import com.example.outsourcingproject.exceptions.TaskException;
import com.example.outsourcingproject.exceptions.UserException;
import com.example.outsourcingproject.repository.TaskRepository;
import com.example.outsourcingproject.repository.UserRepository;
import com.example.outsourcingproject.type.Priority;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskResponse createTask(Long userId, TaskRequest request) {

        // 인가) userId로 로그인한 유저 찾기
        User loginUser = userRepository.findById(userId).orElseThrow(() -> new UserException("해당 유저가 존재하지 않습니다."));
        // string -> enum 타입 변환
        Priority priority = Priority.fromString(request.getPriority());

        // assigneeId로 User엔티티를 조회해서
        // 1. 담당자가 존재하는 지 확인
        User assignee = userRepository.findById(request.getAssigneeId()).orElseThrow(() -> new UserException("해당 유저가 존재하지 않습니다."));

        // 2. 존재하면, 리포지토리 저장 및 반환
        Task task = new Task(request.getTitle(), request.getDescription(), priority, request.getDueDate(), assignee, loginUser);
        Task savedTask = taskRepository.save(task);
        return new TaskResponse(savedTask);
    }

    public List<TaskResponse> getTasks() {

        // 리포지토리에서 삭제되지 않은 List<Task> 가져오기
        List<Task> taskList = taskRepository.findAllByIsDeletedFalse();

        // stream()과 람다를 사용해서 responses에 담기
        List<TaskResponse> responses = taskList.stream()
                                                .map(TaskResponse::new)
                                                .collect(Collectors.toList());
        return responses;
    }

    public TaskResponse updateTask(Long taskId, TaskUpdateRequest request) {

        // taskId로 task 찾기
        Task task = taskRepository.findByIdAndIsDeletedFalse(taskId).orElseThrow(() -> new TaskException("해당 taskId는 없거나 삭제되었습니다."));

        // 해당 task의 title/description/priority/assignee 수정
        if(request.getTitle() != null) {
            task.changeTitle(request.getTitle());
        }
        if(request.getDescription() != null) {
            task.changeDescription(request.getDescription());
        }
        if(request.getPriority() != null) {
            task.changePriority(request.getPriority());
        }
        if(request.getDueDate() != null) {
            task.changeDueDate(request.getDueDate());
        }
        if(request.getAssigneeId() != null) {
            User assignee = userRepository.findById(request.getAssigneeId()).orElseThrow(() -> new UserException("해당 유저가 존재하지 않습니다."));
            task.changeAssigneeId(assignee);
        }
        // 리포지토리 저장 및 반환
        Task savedTask = taskRepository.save(task);
        return new TaskResponse(savedTask);
    }

    public TaskUpdateStatusResponse updateStatus(Long taskId) {

        Task task = taskRepository.findByIdAndIsDeletedFalse(taskId).orElseThrow(() -> new TaskException("해당 taskId는 없거나 삭제되었습니다."));
        // 투두 →IN_PROGRESS → DONE 순서로만 변경 가능
        task.changeStatus();

        Task savedTask = taskRepository.save(task);
        return new TaskUpdateStatusResponse(savedTask);
    }

    public TaskDeleteResponse deleteTask(Long userId, Long taskId) {

        // 인가) userId로 로그인한 유저 찾기
        User loginUser = userRepository.findById(userId).orElseThrow(() -> new UserException("해당 유저를 찾을 수 없습니다."));
        // 인가) userId와 taskId가 일치하는지 검사
        Task task = taskRepository.findByIdAndIsDeletedFalse(taskId).orElseThrow(() -> new TaskException("해당 taskId는 없거나 삭제되었습니다."));
        if(!task.getCreatedBy().getUserId().equals(loginUser.getUserId())) {
            throw new UserException("해당 task를 수정할 수 없습니다. (로그인 유저와 task 유저 불일치)");
        }

        // soft delete 처리
        task.delete();

        Task savedTask = taskRepository.save(task);

        return new TaskDeleteResponse(savedTask);
    }
}
