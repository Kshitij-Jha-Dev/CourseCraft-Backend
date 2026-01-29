package com.onlineCourse.eduhub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlineCourse.eduhub.entity.LessonMaterial;

public interface LessonMaterialRepository extends JpaRepository<LessonMaterial, Long> {

	List<LessonMaterial> findByLessonId(Long lessonId);
	
}
