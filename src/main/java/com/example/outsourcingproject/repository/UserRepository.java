package com.example.outsourcingproject.repository;

import com.example.outsourcingproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);
    Optional<User> findUserByUsername(String username);
    void deleteByUsername(String username);
}
