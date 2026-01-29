package com.onlineCourse.eduhub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlineCourse.eduhub.entity.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

	List<Lesson> findBySectionIdOrderBySequenceNo(Long sectionId);
	
	// show preview lessons for a course
	List<Lesson> findByIsPreviewTrueAndSectionCourseId(Long courseId);
}
