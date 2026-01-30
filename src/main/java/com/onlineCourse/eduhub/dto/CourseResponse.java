package com.onlineCourse.eduhub.dto;

import java.math.BigDecimal;
import java.util.Set;

import com.onlineCourse.eduhub.enums.CourseLevel;
import com.onlineCourse.eduhub.enums.CourseMode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class CourseResponse {

    private Long id;
    private String title;
    private String description;
    private String thumbnailUrl;

    private BigDecimal price;
    private CourseLevel level;
    private CourseMode mode;
    private String language;

    private TrainerSummary trainer;
    private Set<String> topics;

    private boolean enrolled;
    
    private SyllabusDTO syllabus;
    
    private long enrollments;
}