package com.onlineCourse.eduhub.repository;

import com.onlineCourse.eduhub.entity.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactMessageRepository extends JpaRepository<ContactMessage, Integer> {
}