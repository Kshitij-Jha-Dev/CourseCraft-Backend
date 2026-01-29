package com.onlineCourse.eduhub.service.impl;

import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlineCourse.eduhub.dto.CourseResponse;
import com.onlineCourse.eduhub.entity.Course;
import com.onlineCourse.eduhub.repository.CourseRepository;
import com.onlineCourse.eduhub.repository.EnrollmentRepository;
import com.onlineCourse.eduhub.security.SecurityUtil;
import com.onlineCourse.eduhub.service.CourseService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final SecurityUtil securityUtil;

    @Override
    public List<CourseResponse> getAllPublishedCourses() {

        List<Course> courses = courseRepository.findByIsPublishedTrue();

        return mapCourses(courses);
    }

    private List<CourseResponse> mapCourses(List<Course> courses) {

        var enrolledIds = securityUtil.getCurrentUserEmail()
                .map(email -> new HashSet<>(
                        enrollmentRepository.findEnrolledCourseIds(email)
                ))
                .orElse(new HashSet<>());

        return courses.stream()
                .map(course -> new CourseResponse(
                        course.getId(),
                        course.getTitle(),
                        course.getDescription(),
                        course.getPrice(),
                        course.getThumbnailUrl(),
                        enrolledIds.contains(course.getId())
                ))
                .toList();
    }
    
    @Override
    public CourseResponse getCourse(Long id) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        var enrolledIds = securityUtil.getCurrentUserEmail()
                .map(email -> new HashSet<>(
                        enrollmentRepository.findEnrolledCourseIds(email)
                ))
                .orElse(new HashSet<>());

        return new CourseResponse(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getPrice(),
                course.getThumbnailUrl(),
                enrolledIds.contains(course.getId())
        );
    }
}