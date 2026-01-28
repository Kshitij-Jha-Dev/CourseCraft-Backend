package com.onlineCourse.eduhub.dto.user;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {

    private Integer id;
    private String name;
    private String email;
    private String role;
    private LocalDateTime createdAt;
    
}