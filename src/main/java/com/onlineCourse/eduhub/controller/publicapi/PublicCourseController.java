package com.onlineCourse.eduhub.controller.publicapi;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onlineCourse.eduhub.repository.CourseRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PublicCourseController {

    private final CourseRepository courseRepository;

    // Get all courses
    @GetMapping("/allcourses")
    public ResponseEntity<?> getAllCourses() {
        var courses = courseRepository.findAll();

        return ResponseEntity.ok(Map.of(
                "success", true,
                "count", courses.size(),
                "data", courses
        ));
    }

    //️Search courses by keyword
    @GetMapping("/search")
    public ResponseEntity<?> searchCourses(@RequestParam String keyword) {

        var results = courseRepository.searchCourses(keyword);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "keyword", keyword,
                "count", results.size(),
                "data", results
        ));
    }
}