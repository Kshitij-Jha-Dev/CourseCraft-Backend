package com.onlineCourse.eduhub.service;

import com.onlineCourse.eduhub.dto.user.UpdateProfileRequest;
import com.onlineCourse.eduhub.dto.user.UserResponse;

import java.util.List;

public interface AdminUserService {

    List<UserResponse> getAllUsers();

    UserResponse updateUser(Long userId, UpdateProfileRequest request);

    void deleteUser(Long userId);
    
    void updateUserRole(Long userId, String role);
}