package com.onlineCourse.eduhub.controller.admin;

import com.onlineCourse.eduhub.dto.user.UpdateProfileRequest;
import com.onlineCourse.eduhub.dto.user.UpdateUserRoleRequest;
import com.onlineCourse.eduhub.service.AdminUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminUserService adminUserService;

    // Get all users
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        var users = adminUserService.getAllUsers();

        return ResponseEntity.ok(Map.of(
                "success", true,
                "count", users.size(),
                "data", users
        ));
    }

    // Update user (no role change)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProfileRequest request) {

        var updatedUser = adminUserService.updateUser(id, request);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "User updated successfully",
                "data", updatedUser
        ));
    }
    
    @PutMapping("/{id}/role")
    public ResponseEntity<?> updateUserRole(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRoleRequest request) {

        adminUserService.updateUserRole(id, request.getRole());

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "User role updated successfully"
        ));
    }
    

    // Delete user (hard delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {

        adminUserService.deleteUser(id);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "User deleted successfully"
        ));
    }
}