package com.onlineCourse.eduhub.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "trainer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer trainerId;

    @NotBlank(message = "Trainer name is required")
    @Size(min = 3, max = 200)
    @Column(nullable = false)
    private String trainerName;

    @NotBlank(message = "Description is required")
    @Size(min = 10, max = 1000)
    @Column(length = 1000, nullable = false)
    private String description;

    @DecimalMin(value = "0.0")
    @DecimalMax(value = "5.0")
    @Column(nullable = false)
    private Double rating = 4.5;

    @Column
    private String imageUrl;

    // One teacher can teach many courses
    @OneToMany(
    	    mappedBy = "trainer",
    	    cascade = CascadeType.ALL,
    	    orphanRemoval = true
    	)
    	@JsonIgnore
    	private List<Course> courses = new ArrayList<>();
}