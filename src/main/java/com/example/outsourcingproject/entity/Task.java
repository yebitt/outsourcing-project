package com.example.outsourcingproject.entity;

import com.example.outsourcingproject.exceptions.TaskException;
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

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Task() {}

    // TaskRequest dto에 대한 생성자
    public Task(String title, String description, Priority priority, LocalDateTime dueDate, User assignee, User creator) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
        this.assignee = assignee;

        this.createdBy = creator;
    }

    // 필드 변경/삭제 메서드들

    public void changeStatus() {

        if(this.startDate == null) {
            this.status = Status.IN_PROGRESS;
            this.startDate = LocalDateTime.now(); // 시작일 설정
        } else if(this.status == Status.IN_PROGRESS) {
            this.status = Status.DONE;
        } else {
            throw new TaskException("이미 DONE 상태입니다.");
        }
    }

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeDescription(String description) {
        this.description = description;
    }

    public void changePriority(String strPriority) {
        Priority priority = Priority.fromString(strPriority);
        this.priority = priority;
    }

    public void changeDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public void changeAssigneeId(User assignee) {
        this.assignee = assignee;
    }

    public void delete() {
        this.isDeleted = true;
        // 삭제일 설정
        this.deletedAt = LocalDateTime.now();
    }

}
