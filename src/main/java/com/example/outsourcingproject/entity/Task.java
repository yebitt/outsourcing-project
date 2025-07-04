package com.example.outsourcingproject.entity;

import com.example.outsourcingproject.type.Priority;
import com.example.outsourcingproject.type.Status;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
public class Task extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority;

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private User assignee; // 담당자

    @ManyToOne
    @JoinColumn(name = "createdBy_id")
    private User createdBy; // 생성자

    @Column(name = "due_date")
    private LocalDateTime dueDate; // 마감일

    @Column(name = "start_date")
    private LocalDateTime startDate; // 시작일 : Status.IN_PROGRESS 될 때가 변경 날짜

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.TODO; // 기본값 - 투두

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Task() {}

    public Task(String title, String description, Priority priority, User assignee,
                User createdBy, LocalDateTime dueDate, LocalDateTime startDate, boolean isDeleted, LocalDateTime deletedAt) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.assignee = assignee;
        this.createdBy = createdBy;
        this.dueDate = dueDate;
        this.startDate = startDate;
        this.isDeleted = isDeleted;
        this.deletedAt = deletedAt;
    }

    // TaskRequest dto에 대한 생성자
    public Task(String title, String description, Priority priority, LocalDateTime dueDate, User assignee) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
        this.assignee = assignee;
    }

    public void changeStatus(Status status) {
        this.status = status;
        // 시작일 설정
        if(status == Status.IN_PROGRESS && this.startDate == null) {
            this.startDate = LocalDateTime.now();
        }
    }

}
