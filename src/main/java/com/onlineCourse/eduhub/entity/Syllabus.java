package com.onlineCourse.eduhub.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "syllabus")
@Getter
@Setter
@NoArgsConstructor
public class Syllabus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ONE syllabus belongs to ONE course
    @OneToOne
    @JoinColumn(name = "course_id", nullable = false, unique = true)
    private Course course;

    // ONE syllabus has MANY lessons
    @OneToMany(
        mappedBy = "syllabus",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @OrderBy("lessonNo ASC")
    private List<Lesson> lessons = new ArrayList<>();
    
    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
        lesson.setSyllabus(this);
    }
}