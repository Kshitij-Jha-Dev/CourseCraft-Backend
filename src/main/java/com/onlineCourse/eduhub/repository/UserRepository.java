package com.onlineCourse.eduhub.repository;

import java.time.Instant;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.onlineCourse.eduhub.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    
    long count();

    @Query("SELECT MAX(u.createdAt) FROM User u")
    Instant lastUserRegistered();
    
    boolean existsByEmailIgnoreCase(String email);
}

