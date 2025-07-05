package com.example.outsourcingproject.dto.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TaskRequest {

    @NotBlank(message = "공백/null은 입력 불가합니다.")
    @Size(max = 15, message = "최대 15글자 입니다.")
    private String title;

    @NotBlank(message = "공백/null은 입력 불가합니다.")
    private String description;

    @NotBlank(message = "우선순위는 필수입니다.")
    private String priority;

    @NotNull(message = "마감일은 필수입니다.")
    private LocalDateTime dueDate;

    private Long assigneeId;

}
