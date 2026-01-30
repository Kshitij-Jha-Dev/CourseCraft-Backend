package com.onlineCourse.eduhub.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "lessons",
       uniqueConstraints = {
           @UniqueConstraint(columnNames = {"syllabus_id", "lesson_no"})
       })
@Getter
@Setter
@NoArgsConstructor
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String lessonName;

    @Column(nullable = false)
    private Integer lessonNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "syllabus_id", nullable = false)
    private Syllabus syllabus;

    @OneToMany(
        mappedBy = "lesson",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @OrderBy("id ASC")
    private Set<LessonMaterial> materials = new HashSet<>();
    
    
    public void addMaterial(LessonMaterial material) {
        materials.add(material);
        material.setLesson(this);
    }

    public void removeMaterial(LessonMaterial material) {
        materials.remove(material);
        material.setLesson(null);
    }
}