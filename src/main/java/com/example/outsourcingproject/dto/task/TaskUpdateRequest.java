package com.example.outsourcingproject.dto.task;

import com.example.outsourcingproject.entity.User;
import com.example.outsourcingproject.type.Priority;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class TaskUpdateRequest {

    @Size(max = 15, message = "최대 15글자 입니다.")
    private String title;
    private String description;
    private Priority priority;
    private User assignee;

    public TaskUpdateRequest(String title, String description, Priority priority, User assignee) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.assignee = assignee;
    }
}
