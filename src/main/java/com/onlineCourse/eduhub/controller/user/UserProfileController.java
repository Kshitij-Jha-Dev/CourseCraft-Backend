package com.onlineCourse.eduhub.controller.user;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlineCourse.eduhub.dto.user.UpdateProfileRequest;
import com.onlineCourse.eduhub.entity.User;
import com.onlineCourse.eduhub.repository.UserRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @PutMapping("/update")
    public ResponseEntity<?> updateProfile(
            @Valid @RequestBody UpdateProfileRequest request) {

        // Get logged-in user
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update name (if provided)
        if (request.getName() != null) {
            user.setName(request.getName());
        }

        // Update email (if provided & changed)
        if (request.getEmail() != null &&
            !request.getEmail().equals(user.getEmail())) {

            if (userRepository.existsByEmail(request.getEmail())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                        "success", false,
                        "errorCode", "EMAIL_ALREADY_EXISTS",
                        "message", "Email already in use"
                ));
            }

            user.setEmail(request.getEmail());
        }

        // Update password (if provided)
        if (request.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        // Save
        userRepository.save(user);

        // Return response
        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Profile updated successfully"
        ));
    }
}
