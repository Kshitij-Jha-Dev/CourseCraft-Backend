package com.onlineCourse.eduhub.service;

import java.util.List;

import com.onlineCourse.eduhub.dto.CourseResponse;

public interface CourseService {

    List<CourseResponse> getAllPublishedCourses();

    CourseResponse getCourse(Long id);
}
