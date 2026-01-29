package com.onlineCourse.eduhub.dto.trainer;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class TrainerRequest {

    @NotBlank
    @Size(min = 3, max = 200)
    private String name;

    @NotBlank
    @Size(min = 10, max = 1000)
    private String description;

    @DecimalMin("0.0")
    @DecimalMax("5.0")
    private Double rating;

    private String imageUrl;
}