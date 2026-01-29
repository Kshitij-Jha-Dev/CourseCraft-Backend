package com.onlineCourse.eduhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.onlineCourse.eduhub.entity.Trainer;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {

    boolean existsByNameIgnoreCase(String name);

}