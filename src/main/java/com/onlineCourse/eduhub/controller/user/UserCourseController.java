package com.onlineCourse.eduhub.controller.user;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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