package com.onlineCourse.eduhub.controller.publicapi;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onlineCourse.eduhub.entity.Course;
import com.onlineCourse.eduhub.repository.CourseRepository;
import com.onlineCourse.eduhub.repository.EnrollmentRepository;
import com.onlineCourse.eduhub.security.SecurityUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PublicCourseController {

    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final SecurityUtil securityUtil;

    @GetMapping("/allcourses")
    public ResponseEntity<?> getAllCourses() {

        var courses = courseRepository.findAll();
        
        enrichEnrollmentFlag(courses);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "count", courses.size(),
                "data", courses
        ));
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchCourses(@RequestParam String keyword) {

        var courses = courseRepository.searchCourses(keyword);
        
        enrichEnrollmentFlag(courses);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "keyword", keyword,
                "count", courses.size(),
                "data", courses
        ));
    }

    
    @GetMapping("/courses/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Integer id) {

        Optional<Course> courseOpt = courseRepository.findById(id);

        if (courseOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of(
                    "success", false,
                    "message", "Course not found"
            ));
        }
        
        Course course = courseOpt.get();
        enrichEnrollmentFlag(List.of(course));

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Course fetched successfully",
                "data", course
        ));
    }
    
    // Shared logic
    private void enrichEnrollmentFlag(List<Course> courses) {

        securityUtil.getCurrentUserEmail()
            .ifPresent(email -> {
                var enrolledIds = new HashSet<>(
                        enrollmentRepository.findEnrolledCourseIds(email)
                );

                courses.forEach(course ->
                        course.setEnrolled(enrolledIds.contains(course.getId()))
                );
            });
    }
}