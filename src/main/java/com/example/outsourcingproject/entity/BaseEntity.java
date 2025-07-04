package com.example.outsourcingproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreatedDate
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;
    // JPA가 update 쿼리를 실행할 때 호출 : (생성시 updatedAt = null이다가, 수정 때만 날짜 저장됨)
    @PreUpdate
    public void setUpdatedAt(){
        this.updatedAt = LocalDateTime.now();
    }
}
