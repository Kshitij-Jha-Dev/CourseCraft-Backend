package com.onlineCourse.eduhub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlineCourse.eduhub.entity.CourseSection;

public interface CourseSectionRepository extends JpaRepository<CourseSection, Long> {

	List<CourseSection> findByCourseIdOrderBySequenceNo(Long courseId);
	
}
