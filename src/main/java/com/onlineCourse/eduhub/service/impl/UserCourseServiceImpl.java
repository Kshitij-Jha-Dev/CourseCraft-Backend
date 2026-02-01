package com.onlineCourse.eduhub.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlineCourse.eduhub.dto.CourseResponse;
import com.onlineCourse.eduhub.dto.LessonDTO;
import com.onlineCourse.eduhub.dto.LessonMaterialDTO;
import com.onlineCourse.eduhub.dto.SyllabusDTO;
import com.onlineCourse.eduhub.dto.TrainerSummary;
import com.onlineCourse.eduhub.dto.user.CourseProgressUpdate;
import com.onlineCourse.eduhub.dto.user.UpdateProgressRequest;
import com.onlineCourse.eduhub.entity.Course;
import com.onlineCourse.eduhub.entity.Enrollment;
import com.onlineCourse.eduhub.entity.Lesson;
import com.onlineCourse.eduhub.entity.LessonMaterial;
import com.onlineCourse.eduhub.entity.Syllabus;
import com.onlineCourse.eduhub.entity.User;
import com.onlineCourse.eduhub.exception.ResourceNotFoundException;
import com.onlineCourse.eduhub.repository.CourseRepository;
import com.onlineCourse.eduhub.repository.EnrollmentRepository;
import com.onlineCourse.eduhub.repository.UserRepository;
import com.onlineCourse.eduhub.security.SecurityUtil;
import com.onlineCourse.eduhub.service.UserCourseService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserCourseServiceImpl implements UserCourseService {

    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final SecurityUtil securityUtil;

    @Override
    public List<CourseResponse> getMyCourses() {

        String email = securityUtil.getCurrentUserEmail()
                .orElseThrow(() -> new AccessDeniedException("Unauthorized"));

        var enrollments = enrollmentRepository.findEnrollmentsByUserEmail(email);

        return enrollments.stream()
                .map(e -> toResponse(e.getCourse(), true, e.getProgress()))
                .toList();
    }

    @Override
    @Transactional
    public void enrollInCourse(Long courseId) {

        String email = securityUtil.getCurrentUserEmail()
                .orElseThrow(() -> new AccessDeniedException("Unauthorized"));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Course course = courseRepository.findByIdAndIsPublishedTrue(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        if (enrollmentRepository.existsByUserIdAndCourseId(user.getId(), courseId)) {
            throw new RuntimeException("User already enrolled in this course");
        }

        Enrollment enrollment = Enrollment.builder()
                .user(user)
                .course(course)
                .build();

        enrollmentRepository.save(enrollment);
    }

    @Override
    @Transactional
    public void unenrollFromCourse(Long courseId) {

        String email = securityUtil.getCurrentUserEmail()
                .orElseThrow(() -> new AccessDeniedException("Unauthorized"));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Enrollment enrollment = enrollmentRepository
                .findByUserIdAndCourseId(user.getId(), courseId)
                .orElseThrow(() -> new RuntimeException("User is not enrolled"));

        enrollmentRepository.delete(enrollment);
    }
    
    @Override
    @Transactional
    public void updateProgress(UpdateProgressRequest request) {

        String email = securityUtil.getCurrentUserEmail()
                .orElseThrow(() -> new AccessDeniedException("Unauthorized"));

        for (CourseProgressUpdate u : request.updates()) {

            Enrollment enrollment = enrollmentRepository
                    .findByUserEmailAndCourseId(email, u.courseId())
                    .orElseThrow(() -> new RuntimeException("Not enrolled"));

            enrollment.setProgress(u.progress());
        }
    }
    
    
    
    private CourseResponse toResponse(
            Course course,
            boolean enrolled,
            Integer progress
    ) {
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
                .progress(progress == null ? BigDecimal.ZERO : BigDecimal.valueOf(progress))
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