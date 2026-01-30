package com.onlineCourse.eduhub.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LessonDTO {

    private Long id;
    private String lessonName;
    private Integer lessonNo;

    private List<LessonMaterialDTO> materials;
}
