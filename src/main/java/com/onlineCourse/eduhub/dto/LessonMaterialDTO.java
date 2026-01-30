package com.onlineCourse.eduhub.dto;

import com.onlineCourse.eduhub.enums.MaterialType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LessonMaterialDTO {

    private Long id;
    private String title;
    private String path;
    private MaterialType type;
    private Integer sequenceNo;
}