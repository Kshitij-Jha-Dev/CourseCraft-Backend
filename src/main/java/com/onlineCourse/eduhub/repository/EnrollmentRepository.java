package com.onlineCourse.eduhub.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.onlineCourse.eduhub.entity.Course;
import com.onlineCourse.eduhub.entity.Enrollment;
import com.onlineCourse.eduhub.entity.User;


@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    // Fetch all enrollments of a user
    List<Enrollment> findByUser(User user);

    // Prevent duplicate enrollment
    boolean existsByUserIdAndCourseId(Long userId, Long courseId);

    // Used for "My Courses"
    Optional<Enrollment> findByUserIdAndCourseId(Long userId, Long courseId);

    // Dashboard metrics
    long count();

    long countByCourseId(Long courseId);

    @Query("""
        select e.course.id
        from Enrollment e
        where e.user.email = :email
    """)
    List<Long> findEnrolledCourseIds(@Param("email") String email);

    @Query("""
        SELECT COALESCE(SUM(c.price), 0)
        FROM Enrollment e
        JOIN e.course c
    """)
    Long totalRevenue();

    @Query("SELECT MAX(e.enrolledAt) FROM Enrollment e")
    Instant lastEnrollment();
    
    @Query("""
    		select e.course
    		from Enrollment e
    		where e.user.email = :email
    		""")
    		List<Course> findCoursesByUserEmail(@Param("email") String email);
}