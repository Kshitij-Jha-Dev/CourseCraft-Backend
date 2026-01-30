package com.onlineCourse.eduhub.service.impl;

import java.math.BigDecimal;
import java.time.Instant;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlineCourse.eduhub.dto.admin.AdminDashboardResponse;
import com.onlineCourse.eduhub.entity.User;
import com.onlineCourse.eduhub.repository.CourseRepository;
import com.onlineCourse.eduhub.repository.EnrollmentRepository;
import com.onlineCourse.eduhub.repository.UserRepository;
import com.onlineCourse.eduhub.service.AdminDashboardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminDashboardServiceImpl implements AdminDashboardService {

    private final CourseRepository courseRepo;
    private final EnrollmentRepository enrollmentRepo;
    private final UserRepository userRepo;

    @Override
    @Transactional(readOnly = true)
    @Cacheable("admin-dashboard")
    public AdminDashboardResponse getDashboardStats() {

        long totalCourses = courseRepo.count();      
        long totalEnrollments = enrollmentRepo.count();
        long totalStudents = userRepo.countByRole(User.ROLE_USER);         

        Long revenue = enrollmentRepo.totalRevenue();
        
        log.info("Calculated total revenue: {}", revenue);
        
        BigDecimal totalRevenue =
                revenue == null ? BigDecimal.ZERO : BigDecimal.valueOf(revenue);
        
        Instant lastEnrollment = enrollmentRepo.lastEnrollment();
        
        Instant lastCoursePublished = courseRepo.lastPublishedCourse();

        Instant lastUserRegistered = userRepo.lastUserRegistered();

        return AdminDashboardResponse.from(
            totalCourses,
            totalEnrollments,
            totalStudents,
            totalRevenue,
            lastEnrollment,
            lastCoursePublished,
            lastUserRegistered
        );
    }
}