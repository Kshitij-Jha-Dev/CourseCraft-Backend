package com.onlineCourse.eduhub.controller.user;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onlineCourse.eduhub.dto.user.UpdateProgressRequest;
import com.onlineCourse.eduhub.service.UserCourseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserCourseController {

    private final UserCourseService userCourseService;

    @GetMapping("/mycourses")
    public ResponseEntity<?> getMyCourses() {

        var courses = userCourseService.getMyCourses();

        return ResponseEntity.ok(Map.of(
                "success", true,
                "count", courses.size(),
                "data", courses
        ));
    }
    
    @PutMapping("/progress")
    public ResponseEntity<?> updateProgress(
            @RequestBody UpdateProgressRequest request) {

        userCourseService.updateProgress(request);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Progress updated successfully"
        ));
    }

    @PostMapping("/enroll")
    public ResponseEntity<?> enrollInCourse(@RequestParam Long courseId) {

        userCourseService.enrollInCourse(courseId);

        return ResponseEntity.status(201).body(Map.of(
                "success", true,
                "message", "Successfully enrolled in course",
                "courseId", courseId
        ));
    }
    
    @PostMapping("/unenroll")
    public ResponseEntity<?> unenrollFromCourse(@RequestParam Long courseId) {

        userCourseService.unenrollFromCourse(courseId);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Successfully unenrolled from course",
                "courseId", courseId
        ));
    }
}