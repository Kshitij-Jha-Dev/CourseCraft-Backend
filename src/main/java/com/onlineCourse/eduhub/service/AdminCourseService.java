package com.onlineCourse.eduhub.service;

import java.util.List;

import com.onlineCourse.eduhub.dto.CourseRequest;
import com.onlineCourse.eduhub.dto.admin.AdminCourseResponse;

public interface AdminCourseService {

	AdminCourseResponse createCourse(CourseRequest request);

    AdminCourseResponse updateCourse(Long id, CourseRequest request);

    AdminCourseResponse getCourse(Long id);

    List<AdminCourseResponse> getAllCourses();

    void deleteCourse(Long id);
}
