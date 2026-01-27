package com.onlineCourse.eduhub.repository;

import com.onlineCourse.eduhub.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Integer> {

    @Query("""
        SELECT c FROM Course c
        WHERE lower(c.courseName) LIKE lower(concat('%', :keyword, '%'))
           OR lower(c.courseDescription) LIKE lower(concat('%', :keyword, '%'))
           OR lower(c.trainer) LIKE lower(concat('%', :keyword, '%'))
    """)
    List<Course> searchCourses(@Param("keyword") String keyword);
}