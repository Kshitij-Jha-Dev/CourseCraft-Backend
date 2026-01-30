package com.onlineCourse.eduhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlineCourse.eduhub.entity.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

}
