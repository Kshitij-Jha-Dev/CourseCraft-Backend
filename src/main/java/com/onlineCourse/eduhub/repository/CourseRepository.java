package com.onlineCourse.eduhub.repository;

import com.onlineCourse.eduhub.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {
}