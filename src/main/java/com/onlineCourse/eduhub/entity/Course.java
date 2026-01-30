package com.onlineCourse.eduhub.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.onlineCourse.eduhub.enums.CourseLevel;
import com.onlineCourse.eduhub.enums.CourseMode;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
	    name = "courses",
	    indexes = {
	        @Index(name = "idx_course_trainer", columnList = "trainer_id")
	    },
	    uniqueConstraints = {
	        @UniqueConstraint(name = "uk_course_title", columnNames = "title")
	    }
	)
@Getter
@Setter
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String thumbnailUrl;

    @Enumerated(EnumType.STRING)
    private CourseLevel level;

    @Enumerated(EnumType.STRING)
    private CourseMode mode;

    private BigDecimal price;

    private String language;

    private Boolean isPublished = false;

    private Instant createdAt;

    private Instant updatedAt;
    
    @ElementCollection
    @CollectionTable(
        name = "course_topics",
        joinColumns = @JoinColumn(name = "course_id")
    ) 
    @Column(name = "topic")
    private Set<String> topics = new HashSet<>();
    
    @OneToOne(
    	    mappedBy = "course",
    	    cascade = CascadeType.ALL,
    	    orphanRemoval = true,
    	    fetch = FetchType.LAZY
    	)
    private Syllabus syllabus;

    // MANY COURSES → ONE TRAINER
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id")
    @JsonIgnoreProperties("courses")
    private Trainer trainer;

    @PrePersist
    void onCreate() {
        createdAt = Instant.now();
        updatedAt = Instant.now();
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = Instant.now();
    }
    
    public void setSyllabus(Syllabus syllabus) {
        this.syllabus = syllabus;
        if (syllabus != null) {
            syllabus.setCourse(this);
        }
    }
}