package com.onlineCourse.eduhub.controller.admin;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlineCourse.eduhub.dto.user.UpdateProfileRequest;
import com.onlineCourse.eduhub.service.ProfileService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminProfileController {

    private final ProfileService profileService;

    @PutMapping("/update")
    public ResponseEntity<?> updateAdminProfile(
            @Valid @RequestBody UpdateProfileRequest request) {

        profileService.updateCurrentUserProfile(request);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Profile updated successfully"
        ));
    }
}