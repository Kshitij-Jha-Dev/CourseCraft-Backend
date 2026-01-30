package com.onlineCourse.eduhub.controller.publicapi;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onlineCourse.eduhub.service.CourseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PublicCourseController {

    private final CourseService courseService;

    @GetMapping("/allcourses")
    public ResponseEntity<?> getAllCourses() {

        var courses = courseService.getVisibleCourses();

        return ResponseEntity.ok(Map.of(
                "success", true,
                "count", courses.size(),
                "data", courses
        ));
    }

    @GetMapping("/courses/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id) {

        var course = courseService.getCourse(id);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "data", course
        ));
    }
    
    @GetMapping("/search")
    public ResponseEntity<?> searchCourses(@RequestParam String keyword) {

        var courses = courseService.searchCourses(keyword);
        
        return ResponseEntity.ok(Map.of(
                "success", true,
                "keyword", keyword,
                "count", courses.size(),
                "data", courses
        ));
    }
}