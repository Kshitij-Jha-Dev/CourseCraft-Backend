package com.onlineCourse.eduhub.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlineCourse.eduhub.dto.user.UpdateProfileRequest;
import com.onlineCourse.eduhub.entity.User;
import com.onlineCourse.eduhub.repository.UserRepository;
import com.onlineCourse.eduhub.security.SecurityUtil;
import com.onlineCourse.eduhub.service.ProfileService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecurityUtil securityUtil;

    @Override
    public void updateCurrentUserProfile(UpdateProfileRequest request) {

        String email = securityUtil.getCurrentUserEmail()
                .orElseThrow(() -> new RuntimeException("Unauthorized"));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update name
        if (request.getName() != null) {
            user.setName(request.getName());
        }

        if (request.getImageUrl() != null) {
            user.setImageUrl(request.getImageUrl());
        }
        
        // Update email (check uniqueness)
        if (request.getEmail() != null &&
            !request.getEmail().equals(user.getEmail())) {

        	 throw new RuntimeException("User email cannot be changed");
//            if (userRepository.existsByEmail(request.getEmail())) {
//                throw new RuntimeException("Email already in use");
//            }
//            user.setEmail(request.getEmail());
        }

        // Update password
        if (request.getPassword() != null) {
            user.setPassword(
                    passwordEncoder.encode(request.getPassword())
            );
        }

        userRepository.save(user);
    }

}
