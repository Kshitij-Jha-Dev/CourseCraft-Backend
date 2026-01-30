package com.onlineCourse.eduhub.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SyllabusDTO {

    private Long id;
    private List<LessonDTO> lessons;
}
