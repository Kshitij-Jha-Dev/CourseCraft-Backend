package com.onlineCourse.eduhub.dto.admin;

import java.math.BigDecimal;
import java.time.Instant;

import com.onlineCourse.eduhub.util.TimeUtil;

public record AdminDashboardResponse(
    long totalCourses,
    long totalEnrollments,
    long totalStudents,
    BigDecimal totalRevenue,
    String recentEnrollmentAgo,
    String recentCoursePublishedAgo,
    String recentUserRegisteredAgo
) {
    public static AdminDashboardResponse from(
            long totalCourses,
            long totalEnrollments,
            long totalStudents,
            BigDecimal totalRevenue,
            Instant lastEnrollment,              
            Instant lastCoursePublished,
            Instant lastUserRegistered
    ) {
        return new AdminDashboardResponse(
            totalCourses,
            totalEnrollments,
            totalStudents,
            totalRevenue,
            TimeUtil.timeAgo(lastEnrollment),
            TimeUtil.timeAgo(lastCoursePublished),
            TimeUtil.timeAgo(lastUserRegistered)
        );
    }
}