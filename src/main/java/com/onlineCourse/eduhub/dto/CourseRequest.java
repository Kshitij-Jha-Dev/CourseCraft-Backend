package com.onlineCourse.eduhub.dto;

import java.math.BigDecimal;

import com.onlineCourse.eduhub.enums.CourseLevel;
import com.onlineCourse.eduhub.enums.CourseMode;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CourseRequest {

    @NotBlank
    private String title;

    private String description;

    private BigDecimal price;

    private CourseLevel level;

    private CourseMode mode;

    private String language;

    private String thumbnailUrl;

    private Boolean isPublished;

    private Long trainerId;
}




