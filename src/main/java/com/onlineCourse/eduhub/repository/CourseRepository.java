package com.onlineCourse.eduhub.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.onlineCourse.eduhub.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {

	@EntityGraph(attributePaths = {
	        "trainer",
	        "topics",
	        "syllabus",
	        "syllabus.lessons",
	        "syllabus.lessons.materials"
	})
	@Query("""
	SELECT c FROM Course c
	LEFT JOIN c.trainer t
	WHERE (
	     lower(c.title) LIKE lower(concat('%', :keyword, '%'))
	  OR lower(c.description) LIKE lower(concat('%', :keyword, '%'))
	  OR lower(t.name) LIKE lower(concat('%', :keyword, '%'))
	)
	ORDER BY c.createdAt DESC
	""")
	List<Course> searchAllCourses(@Param("keyword") String keyword);

	
	@EntityGraph(attributePaths = {
	        "trainer",
	        "topics",
	        "syllabus",
	        "syllabus.lessons",
	        "syllabus.lessons.materials"
	})
	@Query("""
	SELECT c FROM Course c
	LEFT JOIN c.trainer t
	WHERE c.isPublished = true
	AND (
	     lower(c.title) LIKE lower(concat('%', :keyword, '%'))
	  OR lower(c.description) LIKE lower(concat('%', :keyword, '%'))
	  OR lower(t.name) LIKE lower(concat('%', :keyword, '%'))
	)
	ORDER BY c.createdAt DESC
	""")
	List<Course> searchPublishedCourses(@Param("keyword") String keyword);
	
	long count();

	@Query("SELECT MAX(c.createdAt) FROM Course c WHERE c.isPublished = true")
	Instant lastPublishedCourse();
	
	
	@EntityGraph(attributePaths = {
	        "trainer",
	        "topics",
	        "syllabus",
	        "syllabus.lessons",
	        "syllabus.lessons.materials"
	})
	List<Course> findAllByOrderByCreatedAtDesc();
	
	
	@EntityGraph(attributePaths = {
	        "trainer",
	        "topics",
	        "syllabus",
	        "syllabus.lessons",
	        "syllabus.lessons.materials"
	})
	List<Course> findByIsPublishedTrueOrderByCreatedAtDesc();
	
	
	@EntityGraph(attributePaths = {
	        "trainer",
	        "topics",
	        "syllabus",
	        "syllabus.lessons",
	        "syllabus.lessons.materials"
	})
	Optional<Course> findByIdAndIsPublishedTrue(Long id);
	
	boolean existsByTitleIgnoreCase(String title);
	
	@EntityGraph(attributePaths = {
	        "trainer",
	        "topics",
	        "syllabus",
	        "syllabus.lessons",
	        "syllabus.lessons.materials"
	})
	Optional<Course> findById(Long id);
	
	@EntityGraph(attributePaths = {
		    "trainer",
		    "topics",
		    "syllabus",
		    "syllabus.lessons",
		    "syllabus.lessons.materials"
		})
		@Query("""
		SELECT DISTINCT c
		FROM Course c
		JOIN Enrollment e ON e.course = c
		WHERE e.user.email = :email
		""")
		List<Course> findCoursesByUserEmail(@Param("email") String email);
}