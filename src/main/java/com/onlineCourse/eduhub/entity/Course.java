package com.onlineCourse.eduhub.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="COURSE")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Course name is required")
    @Size(min = 3, max = 100, message = "Course name must be between 3 and 100 characters")
    @Column(nullable = false)
    private String courseName;

    @NotBlank(message = "Course description is required")
    @Size(min = 10, max = 1000, message = "Course description must be between 10 and 1000 characters")
    @Column(nullable = false, length = 1000)
    private String courseDescription;

    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be at least 1 hour")
    @Max(value = 1000, message = "Duration cannot exceed 1000 hours")
    @Column(nullable = false)
    private Integer duration;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price cannot be negative")
    @Max(value = 1000000, message = "Price too high")
    @Column(nullable = false)
    private Integer price = 0;

    @NotNull(message = "Rating is required")
    @DecimalMin(value = "0.0", message = "Rating cannot be less than 0")
    @DecimalMax(value = "5.0", message = "Rating cannot be more than 5")
    @Column(nullable = false)
    private Double rating = 4.5;

    @NotBlank(message = "Trainer name is required")
    @Size(min = 3, max = 100, message = "Trainer name must be between 3 and 100 characters")
    @Column(nullable = false)
    private String trainer;

    @Transient
    private boolean isEnrolled;

    @Transient
    private String imageId;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<Enrollment> enrollments = new java.util.ArrayList<>();
}