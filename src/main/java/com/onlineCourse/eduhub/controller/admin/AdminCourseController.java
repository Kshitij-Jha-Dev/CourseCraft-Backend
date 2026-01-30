package com.onlineCourse.eduhub.controller.admin;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlineCourse.eduhub.dto.CourseRequest;
import com.onlineCourse.eduhub.dto.admin.AdminCourseResponse;
import com.onlineCourse.eduhub.service.AdminCourseService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/courses")
@RequiredArgsConstructor
public class AdminCourseController {

    private final AdminCourseService adminCourseService;

    @PostMapping
    public ResponseEntity<?> addCourse(
            @Valid @RequestBody CourseRequest request) {

    	AdminCourseResponse saved = adminCourseService.createCourse(request);

        return ResponseEntity.status(201).body(Map.of(
                "success", true,
                "message", "Course created successfully",
                "data", saved
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id) {

        return ResponseEntity.ok(Map.of(
                "success", true,
                "data", adminCourseService.getCourse(id)
        ));
    }

    @GetMapping
    public ResponseEntity<?> getAllCourses() {

        var courses = adminCourseService.getAllCourses();

        return ResponseEntity.ok(Map.of(
                "success", true,
                "count", courses.size(),
                "data", courses
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(
            @PathVariable Long id,
            @Valid @RequestBody CourseRequest request) {

    	AdminCourseResponse updated = adminCourseService.updateCourse(id, request);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Course updated successfully",
                "data", updated
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {

        adminCourseService.deleteCourse(id);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Course deleted successfully"
        ));
    }
}