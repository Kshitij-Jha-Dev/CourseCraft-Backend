package com.onlineCourse.eduhub.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlineCourse.eduhub.dto.CourseResponse;
import com.onlineCourse.eduhub.dto.LessonDTO;
import com.onlineCourse.eduhub.dto.LessonMaterialDTO;
import com.onlineCourse.eduhub.dto.SyllabusDTO;
import com.onlineCourse.eduhub.dto.TrainerSummary;
import com.onlineCourse.eduhub.entity.Course;
import com.onlineCourse.eduhub.entity.Lesson;
import com.onlineCourse.eduhub.entity.LessonMaterial;
import com.onlineCourse.eduhub.entity.Syllabus;
import com.onlineCourse.eduhub.exception.ResourceNotFoundException;
import com.onlineCourse.eduhub.repository.CourseRepository;
import com.onlineCourse.eduhub.repository.EnrollmentRepository;
import com.onlineCourse.eduhub.security.SecurityUtil;
import com.onlineCourse.eduhub.service.CourseService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final SecurityUtil securityUtil;



    @Override
    public List<CourseResponse> getVisibleCourses() {

        List<Course> courses;

        if (securityUtil.isAdmin()) {
            courses = courseRepository.findAllByOrderByCreatedAtDesc();
        } else {
            courses = courseRepository.findByIsPublishedTrueOrderByCreatedAtDesc();
        }

        Set<Long> enrolledIds = getEnrolledCourseIds();

        boolean isPublic = securityUtil.getCurrentUserEmail().isEmpty();

        return courses.stream()
                .map(course ->
                        toResponse(
                                course,
                                !isPublic && enrolledIds.contains(course.getId()),
                                enrollmentRepository.countByCourseId(course.getId())
                        )
                )
                .toList();
    }


    @Override
    public CourseResponse getCourse(Long id) {

        Course course = fetchCourseBasedOnRole(id);

        boolean enrolled = false;

        // Only check enrollment if user is logged in
        var emailOpt = securityUtil.getCurrentUserEmail();

        if (emailOpt.isPresent()) {
            enrolled = enrollmentRepository
                    .existsByUserEmailAndCourseId(emailOpt.get(), id);
        }

        long count = enrollmentRepository.countByCourseId(id);
        
        return toResponse(course, enrolled, count);
    }
    
    @Override
    public List<CourseResponse> searchCourses(String keyword) {

        List<Course> courses;

        if (securityUtil.isAdmin()) {
            courses = courseRepository.searchAllCourses(keyword);
        } else {
            courses = courseRepository.searchPublishedCourses(keyword);
        }

        Set<Long> enrolledIds = getEnrolledCourseIds();
        boolean isPublic = securityUtil.getCurrentUserEmail().isEmpty();

        return courses.stream()
                .map(course ->
                        toResponse(
                                course,
                                !isPublic && enrolledIds.contains(course.getId()),
                                enrollmentRepository.countByCourseId(course.getId())
                        )
                )
                .toList();
    }

    
    private Course fetchCourseBasedOnRole(Long id) {

        if (securityUtil.isAdmin()) {
            return courseRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        }

        return courseRepository.findByIdAndIsPublishedTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
    }

    private Set<Long> getEnrolledCourseIds() {

        return securityUtil.getCurrentUserEmail()
                .map(email -> new HashSet<>(
                        enrollmentRepository.findEnrolledCourseIds(email)
                ))
                .orElse(new HashSet<>());
    }

   

    private CourseResponse toResponse(
            Course course,
            boolean enrolled,
            long enrollmentCount) {

        TrainerSummary trainerSummary = null;

        if (course.getTrainer() != null) {
            trainerSummary = TrainerSummary.builder()
                    .id(course.getTrainer().getId())
                    .name(course.getTrainer().getName())
                    .rating(course.getTrainer().getRating())
                    .description(course.getTrainer().getDescription())
                    .imageUrl(course.getTrainer().getImageUrl())
                    .build();
        }

        return CourseResponse.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .thumbnailUrl(course.getThumbnailUrl())
                .level(course.getLevel())
                .mode(course.getMode())
                .price(course.getPrice())
                .language(course.getLanguage())
                .topics(course.getTopics())
                .trainer(trainerSummary)
                .syllabus(mapSyllabus(course.getSyllabus()))
                .enrolled(enrolled)
                .enrollments(enrollmentCount)
                .build();
    }


    private SyllabusDTO mapSyllabus(Syllabus syllabus) {

        if (syllabus == null) return null;

        return SyllabusDTO.builder()
                .id(syllabus.getId())
                .lessons(
                        syllabus.getLessons()
                                .stream()
                                .map(this::mapLesson)
                                .toList()
                )
                .build();
    }


    private LessonDTO mapLesson(Lesson lesson) {

        return LessonDTO.builder()
                .id(lesson.getId())
                .lessonName(lesson.getLessonName())
                .lessonNo(lesson.getLessonNo())
                .materials(
                        lesson.getMaterials()
                                .stream()
                                .map(this::mapMaterial)
                                .toList()
                )
                .build();
    }



    private LessonMaterialDTO mapMaterial(LessonMaterial material) {

        return LessonMaterialDTO.builder()
                .id(material.getId())
                .type(material.getType())
                .title(material.getTitle())
                .path(material.getPath())
                .sequenceNo(material.getSequenceNo())
                .build();
    }
}