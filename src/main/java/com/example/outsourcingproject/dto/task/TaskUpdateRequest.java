package com.example.outsourcingproject.dto.task;

import com.example.outsourcingproject.entity.User;
import com.example.outsourcingproject.type.Priority;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TaskUpdateRequest {

    @Size(max = 15, message = "최대 15글자 입니다.")
    private String title;
    private String description;
    private String priority;
    private LocalDateTime dueDate;
    private Long assigneeId;

    // @Validated 붙이면 자동 실행됨
    @AssertTrue(message = "하나이상 반드시 수정해야 합니다.")
    public boolean isValidUpdateRequest() {
        return (title != null) || (description != null)
                || (priority != null) || (dueDate != null) || (assigneeId != null);
    }

}
