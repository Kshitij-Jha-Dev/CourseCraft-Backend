package com.onlineCourse.eduhub.controller.admin;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlineCourse.eduhub.entity.Course;
import com.onlineCourse.eduhub.repository.CourseRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/courses")
@RequiredArgsConstructor
public class AdminCourseController {

    private final CourseRepository courseRepository;

    @PostMapping
    public ResponseEntity<?> addCourse(@Valid @RequestBody Course course) {
        Course savedCourse = courseRepository.save(course);
        return ResponseEntity.status(201).body(Map.of(
        	    "success", true,
        	    "message", "Course created successfully",
        	    "data", savedCourse
        	));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Integer id) {

        Optional<Course> courseOpt = courseRepository.findById(id);

        if (courseOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of(
                    "success", false,
                    "message", "Course not found"
            ));
        }

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Course fetched successfully",
                "data", courseOpt.get()
        ));
    }
    
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable Integer id,
                                         @Valid @RequestBody Course updatedCourse) {

        Optional<Course> courseOpt = courseRepository.findById(id);

        if (courseOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of(
                    "success", false,
                    "message", "Course not found"
            ));
        }

        Course existing = courseOpt.get();

        existing.setCourseName(updatedCourse.getCourseName());
        existing.setCourseDescription(updatedCourse.getCourseDescription());
        existing.setDuration(updatedCourse.getDuration());
        existing.setPrice(updatedCourse.getPrice());
        existing.setRating(updatedCourse.getRating());
        existing.setTrainer(updatedCourse.getTrainer());

        Course saved = courseRepository.save(existing);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Course updated successfully",
                "data", saved
        ));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Integer id) {

        if (!courseRepository.existsById(id)) {
            return ResponseEntity.status(404).body(Map.of(
                    "success", false,
                    "message", "Course not found"
            ));
        }

        courseRepository.deleteById(id);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Course deleted successfully"
        ));
    }
    
    
    
    
}