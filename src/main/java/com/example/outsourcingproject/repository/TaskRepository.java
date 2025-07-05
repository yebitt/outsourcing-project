package com.example.outsourcingproject.repository;

import com.example.outsourcingproject.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // 삭제되지 않은 Task만 조회
    Optional<Task> findByIdAndIsDeletedFalse(Long id);
    // 삭제되지 않은 TaskList 조회
    List<Task> findAllByIsDeletedFalse();

}
