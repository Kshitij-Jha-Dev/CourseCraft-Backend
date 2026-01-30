package com.onlineCourse.eduhub.service;

import java.util.List;

import com.onlineCourse.eduhub.dto.CourseResponse;

public interface UserCourseService {

    List<CourseResponse> getMyCourses();

    void enrollInCourse(Long courseId);

    void unenrollFromCourse(Long courseId);
}