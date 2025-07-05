package com.example.outsourcingproject.dto.task;

import com.example.outsourcingproject.dto.common.UserAssigneeResponse;
import com.example.outsourcingproject.entity.Task;
import com.example.outsourcingproject.type.Priority;
import com.example.outsourcingproject.type.Status;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class TaskUpdateStatusResponse {

    private final Long id;
    private final String title;
    private final String description;
    private final Priority priority;
    private final LocalDateTime dueDate;
    private LocalDateTime startDate;
    private final Status status;

    private final Long assigneeId;
    private final UserAssigneeResponse assignee; // id, username, name, email

    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;


    public TaskUpdateStatusResponse(Task task) {

        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.priority = task.getPriority();
        this.dueDate = task.getDueDate();
        this.startDate = task.getStartDate();
        this.status = task.getStatus();
        this.assigneeId = task.getAssignee().getUserId();
        this.assignee = new UserAssigneeResponse(task.getAssignee().getUserId(), task.getAssignee().getUsername(),
                task.getAssignee().getName(), task.getAssignee().getEmail());
        this.createdAt = task.getCreatedAt();
        this.updatedAt = task.getUpdatedAt();
    }
}
