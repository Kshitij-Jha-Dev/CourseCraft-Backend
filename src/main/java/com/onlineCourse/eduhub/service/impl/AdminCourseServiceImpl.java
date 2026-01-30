package com.onlineCourse.eduhub.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlineCourse.eduhub.dto.CourseRequest;
import com.onlineCourse.eduhub.dto.TrainerSummary;
import com.onlineCourse.eduhub.dto.admin.AdminCourseResponse;
import com.onlineCourse.eduhub.entity.Course;
import com.onlineCourse.eduhub.entity.Lesson;
import com.onlineCourse.eduhub.entity.LessonMaterial;
import com.onlineCourse.eduhub.entity.Syllabus;
import com.onlineCourse.eduhub.entity.Trainer;
import com.onlineCourse.eduhub.exception.ResourceNotFoundException;
import com.onlineCourse.eduhub.repository.CourseRepository;
import com.onlineCourse.eduhub.repository.TrainerRepository;
import com.onlineCourse.eduhub.service.AdminCourseService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminCourseServiceImpl implements AdminCourseService {

    private final CourseRepository courseRepository;
    private final TrainerRepository trainerRepository;

    @Override
    @Transactional
    public AdminCourseResponse createCourse(CourseRequest request) {

    	if (courseRepository.existsByTitleIgnoreCase(request.getTitle().trim())) {
    	    throw new IllegalArgumentException("Course with this title already exists");
    	}

    	Course course = new Course();

    	mapCourseFields(course, request);
    	createSyllabusGraph(course, request);

    	return mapToResponse(courseRepository.save(course));
    }

    @Override
    @Transactional
    public AdminCourseResponse updateCourse(Long id, CourseRequest request) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        if (!course.getTitle().equalsIgnoreCase(request.getTitle()) &&
        	    courseRepository.existsByTitleIgnoreCase(request.getTitle())) {

        	    throw new IllegalArgumentException("Course with this title already exists");
        	}
        
        mapCourseFields(course, request);
        updateSyllabusGraph(course, request);
        
        
        return mapToResponse(course);
    }

    @Override
    @Transactional(readOnly = true)
    public AdminCourseResponse getCourse(Long id) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        return mapToResponse(course);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdminCourseResponse> getAllCourses() {

        return courseRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional
    public void deleteCourse(Long id) {

        if (!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Course not found");
        }

        courseRepository.deleteById(id);
    }

  //Reusable mapper
    private void mapCourseFields(Course course, CourseRequest request) {

        course.setTitle(request.getTitle().trim());
        course.setDescription(request.getDescription());
        course.setPrice(request.getPrice());
        course.setLevel(request.getLevel());
        course.setMode(request.getMode());
        course.setLanguage(request.getLanguage());
        course.setThumbnailUrl(request.getThumbnailUrl());
        course.setIsPublished(request.getIsPublished());
        course.setTopics(request.getTopics());

        if (request.getTrainerId() != null) {

            Trainer trainer = trainerRepository.findById(request.getTrainerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Trainer not found"));

            course.setTrainer(trainer);
        }

     }
    
    private AdminCourseResponse mapToResponse(Course course) {

        TrainerSummary trainerSummary = null;

        if (course.getTrainer() != null) {
            trainerSummary = TrainerSummary.builder()
                    .id(course.getTrainer().getId())
                    .name(course.getTrainer().getName())
                    .rating(course.getTrainer().getRating())
                    .build();
        }

        return AdminCourseResponse.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .thumbnailUrl(course.getThumbnailUrl())
                .level(course.getLevel())
                .mode(course.getMode())
                .price(course.getPrice())
                .language(course.getLanguage())
                .isPublished(course.getIsPublished())
                .createdAt(course.getCreatedAt())
                .updatedAt(course.getUpdatedAt())
                .trainer(trainerSummary)
                .topics(course.getTopics())
                .build();
    }
    
    
    private void createSyllabusGraph(Course course, CourseRequest request) {

        if (request.getSyllabus() == null) return;

        Syllabus syllabus = new Syllabus();
        syllabus.setCourse(course);
        course.setSyllabus(syllabus);

        request.getSyllabus().getLessons().forEach(lessonDTO -> {

            Lesson lesson = new Lesson();
            lesson.setLessonName(lessonDTO.getLessonName());
            lesson.setLessonNo(lessonDTO.getLessonNo());
            lesson.setSyllabus(syllabus);

            syllabus.getLessons().add(lesson);

            if (lessonDTO.getMaterials() != null) {

                lessonDTO.getMaterials().forEach(materialDTO -> {

                    LessonMaterial material = new LessonMaterial();
                    material.setTitle(materialDTO.getTitle());
                    material.setPath(materialDTO.getPath());
                    material.setType(materialDTO.getType());
                    material.setSequenceNo(materialDTO.getSequenceNo());

                    lesson.addMaterial(material);
                });
            }
        });
    }
    
    
    @PersistenceContext
    private EntityManager entityManager;


    private void updateSyllabusGraph(Course course, CourseRequest request) {

        // CASE 1 — remove syllabus completely
        if (request.getSyllabus() == null) {

            if (course.getSyllabus() != null) {
                course.setSyllabus(null);
                entityManager.flush(); // force DELETE cascade NOW
            }

            return;
        }

        // CASE 2 — ALWAYS replace (recommended for LMS)
        if (course.getSyllabus() != null) {

            course.setSyllabus(null);

            // CRITICAL — executes deletes BEFORE inserts
            entityManager.flush();
        }

        // build fresh graph
        createSyllabusGraph(course, request);
    }
    
}
