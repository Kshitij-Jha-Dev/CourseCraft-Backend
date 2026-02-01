package com.onlineCourse.eduhub.service;

import java.util.List;

import com.onlineCourse.eduhub.dto.CourseResponse;
import com.onlineCourse.eduhub.dto.user.UpdateProgressRequest;

public interface UserCourseService {

    List<CourseResponse> getMyCourses();

    void enrollInCourse(Long courseId);

    void unenrollFromCourse(Long courseId);
    
    public void updateProgress(UpdateProgressRequest request);
}