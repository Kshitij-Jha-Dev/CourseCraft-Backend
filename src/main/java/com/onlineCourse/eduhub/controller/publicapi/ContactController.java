package com.onlineCourse.eduhub.controller.publicapi;

import com.onlineCourse.eduhub.entity.ContactMessage;
import com.onlineCourse.eduhub.repository.ContactMessageRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ContactController {

    private final ContactMessageRepository contactMessageRepository;

    @PostMapping("/contactus")
    public ResponseEntity<?> submitContactForm(@Valid @RequestBody ContactMessage contactMessage) {

        ContactMessage saved = contactMessageRepository.save(contactMessage);

        return ResponseEntity.status(201).body(Map.of(
                "success", true,
                "message", "Thank you for contacting us. We will get back to you soon.",
                "id", saved.getId()
        ));
    }
}