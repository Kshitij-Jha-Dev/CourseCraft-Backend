package com.onlineCourse.eduhub.dto.admin;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

import com.onlineCourse.eduhub.dto.TrainerSummary;
import com.onlineCourse.eduhub.enums.CourseLevel;
import com.onlineCourse.eduhub.enums.CourseMode;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminCourseResponse {

    private Long id;
    private String title;
    private String description;
    private String thumbnailUrl;
    private CourseLevel level;
    private CourseMode mode;
    private BigDecimal price;
    private String language;
    private Boolean isPublished;
    private Instant createdAt;
    private Instant updatedAt;

    private TrainerSummary trainer;
    
    private Set<String> topics;
    
    private long enrollments;
}